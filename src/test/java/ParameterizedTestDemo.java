import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.core.model.Gender;
import com.example.core.model.Person;

import argumentprovider.MyArgumentProvider;

import static com.example.core.model.Gender.UNISEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertTrue(Arrays.asList(Gender.WOMAN, UNISEX).contains(gender));
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
	@MethodSource({"fullNameProvider1", "fullNameProvider2"})
	void methodSourceStream(String fullName) {
		assertNotNull(fullName);
		assertTrue(fullName.contains(" "));
	}

	static Stream<String> fullNameProvider1() {
		return Stream.of("Ryosuke Uchitate", "Taro Uchitate");
	}

	static Stream<String> fullNameProvider2() {
		return Stream.of("Michael Jackson", "Janet Jackson");
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
	void argumentSourceArgumentsStream(Person arg) {
		assertNotNull(arg);
		assertTrue(arg.getFullName().contains(" "));
		assertFalse(arg.getAge() < 0);
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
