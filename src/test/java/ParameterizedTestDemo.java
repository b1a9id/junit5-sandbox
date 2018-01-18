import com.example.core.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.*;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;

class ParameterizedTestDemo {

	private Person person1;

	private Person person2;

	@BeforeEach
	void beforeEach() {
		person1 = new Person("Ryosuke", "Uchitate", 27, Gender.MAN);
		person2 = new Person("Taro", "Uchitate", 20, Gender.MAN);
	}

	/**
	 * Value Source
	 */
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

	/**
	 * Enum Source
	 */
	@ParameterizedTest
	@EnumSource(Gender.class)
	void enumSourceAll(Gender gender) {
		assertTrue(Arrays.asList(Gender.values()).contains(gender));
	}

	@ParameterizedTest
	@EnumSource(value = Gender.class, names = {"MAN", "WOMAN"})
	void enumSourceInclude(Gender gender) {
		assertTrue(Arrays.asList(Gender.MAN, Gender.WOMAN).contains(gender));
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

	/**
	 * CSV Source
	 */
	@ParameterizedTest
	@CsvSource({"foo, bar", "foo, 'bar, hoge'", "foo, ''", "foo, "})
	void csvSource(String first, String second) {
		System.out.println(String.format("first: %s, second: %s", first, second));
	}

	/**
	 * CSV File Source
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "test.csv")
	void csvFileSource(String first, int second) {
		assertNotNull(first);
		assertNotEquals(0, second);
	}

	/**
	 * Method Source
	 */
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

	/**
	 * Arguments Source
	 */
	@ParameterizedTest
	@ArgumentsSource(MyArgumentProvider.class)
	void argumentSourceArgumentsStream(String arg) {
		assertNotNull(arg);
	}

	static class MyArgumentProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of("foo", "bar").map(Arguments::of);
		}
	}

	/**
	 * Argument Conversion
	 */
	@ParameterizedTest
	@EnumSource(Gender.class)
	void toStringArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument) {
		assertNotNull(Gender.valueOf(argument));
	}

	static class ToStringArgumentConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(String.class, targetType, "Can only convert to String");
			return String.valueOf(source);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"2017/10/10", "2017/12/12"})
	void javaTimeArgumentConversion(@JavaTimeConversionPattern("yyyy/MM/dd") LocalDate argument) {
		assertEquals(2017, argument.getYear());
	}

	/**
	 * Customizing Display Names
	 */
	@DisplayName("Customizing Display Name")
	@ParameterizedTest(name = "{index} ==> name = ''{0}''")
	@ValueSource(strings = {"Ryosuke", "Taro", "Hanako"})
	void customizeDisplayName(String name) {
		assertNotNull(name);
	}
}
