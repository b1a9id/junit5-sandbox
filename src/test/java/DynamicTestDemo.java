import com.example.core.model.Gender;
import com.example.core.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

class DynamicTestDemo {

	@TestFactory
	Stream<DynamicTest> personDynamicTest() {
		return Stream.of(
				new Person("Ryosuke", "Uchitate", 27, Gender.MAN),
				new Person("Hanako", "Yamada", 15, Gender.WOMAN)
		).map(p -> DynamicTest.dynamicTest("fullName", () -> Assertions.assertEquals(p.getFirstName() + " " + p.getLastName(), p.getFullName())));
	}
}
