import com.example.core.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class ParticularAssertionTest {

	@Nested
	class RepeatedTestDemo {

		@BeforeEach
		void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
			int currentRepetition = repetitionInfo.getCurrentRepetition();
			int totalRepetitions = repetitionInfo.getTotalRepetitions();
			String testClass = testInfo.getTestClass().get().getName();
			String methodName = testInfo.getTestMethod().get().getName();

			System.out.println(String.format("About to execute repetition %d of %d for %s#%s", //
					currentRepetition, totalRepetitions, testClass, methodName));
		}

		@RepeatedTest(3)
		void noArgs() {
			assertTrue("test".length() == 4);
		}

		@RepeatedTest(3)
		void totalRepetitionsInfo(RepetitionInfo repetitionInfo) {
			assertEquals(3, repetitionInfo.getTotalRepetitions());
		}

		@RepeatedTest(value = 3, name = "{displayName} {currentRepetition}/{totalRepetitions}")
		@DisplayName("Custom Repeated Test!!")
		void customDisplayName(TestInfo testInfo, RepetitionInfo repetitionInfo) {
			int currentRepetition = repetitionInfo.getCurrentRepetition();
			int totalRepetitions = repetitionInfo.getTotalRepetitions();
			assertEquals(String.format("Custom Repeated Test!! %d/%d", currentRepetition, totalRepetitions), testInfo.getDisplayName());
		}

		@RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
		@DisplayName("Details...")
		void customDisplayNameLongPattern(TestInfo testInfo) {
			assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
		}
	}

	@Nested
	class ParameterizedTestDemo {

		Person person1;

		Person person2;

		@BeforeEach
		void beforeEach() {
			person1 = new Person("Ryosuke", "Uchitate", 27, Gender.MAN);
			person2 = new Person("Taro", "Uchitate", 20, Gender.MAN);
		}

		@ParameterizedTest
		@ValueSource(strings = {"Ryosuke Uchitate", "Taro Uchitate"})
		void stringsValue(String fullName) {
			List<String> personFullNameList = Arrays.asList(person1.getFullName(), person2.getFullName());
			assertTrue(personFullNameList.contains(fullName));
		}

		@ParameterizedTest
		@ValueSource(ints = {10, 20, 30})
		void intsValue(int age) {
			assertTrue(age > 0);
		}

		@ParameterizedTest
		@EnumSource(value = Gender.class, names = {"MAN", "WOMAN"})
		void enumSourceInclude(Gender gender) {
			assertTrue(Arrays.asList(Gender.values()).contains(gender));
		}

		@ParameterizedTest
		@EnumSource(value = Gender.class, names = {"MAN"}, mode = EnumSource.Mode.EXCLUDE)
		void enumSourceExclude(Gender gender) {
			assertFalse(Collections.singleton(Gender.MAN).contains(gender));
			assertEquals(Gender.WOMAN, gender);
		}

		@ParameterizedTest
		@EnumSource(value = Gender.class, names = ".*MAN$", mode = EnumSource.Mode.MATCH_ALL)
		void enumSourceMatchAll(Gender gender) {
			String name = gender.name();
			assertTrue(name.startsWith("M") || name.startsWith("W"));
			assertTrue(name.contains("MAN"));
		}

		@ParameterizedTest
		@CsvSource({"foo, bar", "foo, 'bar, hoge'", "foo, ''", "foo, "})
		void csvSource(String first, String second) {
			System.out.println(String.format("first: %s, second: %s", first, second));
		}
	}

	@ParameterizedTest
	@MethodSource("fullNameProvider")
	void methodSourceStream(String fullName) {
		assertNotNull(fullName);
		assertTrue(fullName.contains(" "));
	}

	static Stream<String> fullNameProvider() {
		return Stream.of("Ryosuke Uchitate", "Taro Uchitate");
	}

	@ParameterizedTest
	@MethodSource("range")
	void methodSourceIntStream(int value) {
		assertTrue(value > 0 && value < 10);
	}

	static IntStream range() {
		return IntStream.range(1, 10);
	}

	@ParameterizedTest
	@MethodSource("multiArgumentsProvider")
	void multiArguments(String name, int age, Gender gender) {
		assertFalse(name.length() > 100);
		assertTrue( age > 0 && age < 30);
		assertTrue(gender.name().length() > 1);
	}

	static Stream<Arguments> multiArgumentsProvider() {
		return Stream.of(
				Arguments.of("Ryosuke", 27, Gender.MAN),
				Arguments.of("Hanako", 20, Gender.WOMAN)
		);
	}
}
