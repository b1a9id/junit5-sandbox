import com.example.core.model.Gender;
import com.example.core.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class DynamicTestDemo {

	@TestFactory
	Collection<DynamicTest> personDynamicTestCollection() {
		List<Person> people = Arrays.asList(new Person("Ryosuke", "Uchitate", 27, Gender.MAN), new Person("Hanako", "Yamada", 15, Gender.WOMAN));
		return Arrays.asList(
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Ryosuke Uchitate", people.get(0).getFullName())),
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Hanako Yamada", people.get(1).getFullName()))
		);
	}

	@TestFactory
	Iterator<DynamicTest> personDynamicTestIterator() {
		List<Person> people = Arrays.asList(new Person("Ryosuke", "Uchitate", 27, Gender.MAN), new Person("Hanako", "Yamada", 15, Gender.WOMAN));
		return Arrays.asList(
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Ryosuke Uchitate", people.get(0).getFullName())),
				DynamicTest.dynamicTest("1st person test", () -> Assertions.assertEquals("Hanako Yamada", people.get(1).getFullName()))
		).iterator();
	}

	@TestFactory
	Stream<DynamicTest> personDynamicTestStream() {
		return Stream.of(
				new Person("Ryosuke", "Uchitate", 27, Gender.MAN),
				new Person("Hanako", "Yamada", 15, Gender.WOMAN)
		).map(p -> DynamicTest.dynamicTest("fullName", () -> Assertions.assertEquals(p.getFirstName() + " " + p.getLastName(), p.getFullName())));
	}
}
