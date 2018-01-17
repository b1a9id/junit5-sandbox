import com.example.core.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

class DynamicTestDemo {

	@TestFactory
	Collection<DynamicTest> personDynamicTestCollection() {
		List<Person> people = Arrays.asList(new Person("Ryosuke", "Uchitate", 27, Gender.MAN), new Person("Hanako", "Yamada", 15, Gender.WOMAN));
		return Arrays.asList(
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Ryosuke Uchitate", people.get(0).getFullName())),
				DynamicTest.dynamicTest("2nd person test", () -> Assertions.assertEquals("Hanako Yamada", people.get(1).getFullName()))
		);
	}

	@TestFactory
	Iterator<DynamicTest> personDynamicTestIterator() {
		List<Person> people = Arrays.asList(new Person("Ryosuke", "Uchitate", 27, Gender.MAN), new Person("Hanako", "Yamada", 15, Gender.WOMAN));
		return Arrays.asList(
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Ryosuke Uchitate", people.get(0).getFullName())),
				DynamicTest.dynamicTest("2nd person test", () -> Assertions.assertEquals("Hanako Yamada", people.get(1).getFullName()))
		).iterator();
	}

	@TestFactory
	Stream<DynamicTest> personDynamicTestStream() {
		return Stream.of(
				new Person("Ryosuke", "Uchitate", 27, Gender.MAN),
				new Person("Hanako", "Yamada", 15, Gender.WOMAN)
		).map(p -> DynamicTest.dynamicTest("fullName", () -> Assertions.assertEquals(p.getFirstName() + " " + p.getLastName(), p.getFullName())));
	}

	@TestFactory
	Stream<DynamicTest> personFormatTestStream() {
		List<Person> people = new LinkedList<>();
		people.add(new Person("Ryosuke", "Uchitate", 27, Gender.MAN));
		people.add(new Person("Hanako", "Yamada", 15, Gender.WOMAN));
		Iterator<Integer> inputGenerator = IntStream.range(0, people.size()).iterator();

		Function<Integer, String> displayNameGenerator = (input) -> "Element : " + input;

		ThrowingConsumer<Integer> testExecutor = (input) -> {
			Person person = people.get(input);
			String expected = getPersonFormatExpected(input);
			Assertions.assertEquals(expected, FormatUtils.person(person));
		};

		return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
	}

	private String getPersonFormatExpected(int num) {
		List<String> expectedList = new LinkedList<>();
		expectedList.add("Name：Ryosuke Uchitate, Age：27, Gender：MAN");
		expectedList.add("Name：Hanako Yamada, Age：15, Gender：WOMAN");
		return expectedList.get(num);
	}
}
