import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.example.core.model.Gender;
import com.example.core.model.Person;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class StandardAssertionTest {

	@Nested
	class DependentAssertions {

		private Person person;

		@BeforeEach
		void init() {
			person = new Person("Ryosuke", "Uchitate", 27, Gender.MAN);
		}

		@Test
		void nameAssertions() {
			assertAll("This is name test.",
					() -> assertEquals("Ryosuke", person.getFirstName()),
					() -> assertEquals("Uchitate", person.getLastName())
			);
		}

		@Test
		void propertiesAssertions() {
			assertAll("properties",
					() -> {
						String firstName = person.getFirstName();
						assertNotNull(firstName);

						assertAll("firstName",
								() -> assertTrue(firstName.startsWith("R")),
								() -> assertFalse(firstName.endsWith("i"))
						);
					},
					() -> {
						String lastName = person.getLastName();
						assertNotNull(lastName);

						assertAll("lastName",
								() -> assertTrue(lastName.startsWith("U")),
								() -> assertFalse(lastName.endsWith("i"))
						);
					},
					() -> {
						assertAll("age",
								() -> assertTrue(person.getAge() > 0),
								() -> assertFalse(person.getAge() < 0)
						);
					},
					() -> {
						Gender gender = person.getGender();
						assertNotNull(gender);

						assertAll("gender",
								() -> assertTrue(gender == Gender.MAN),
								() -> assertFalse(gender == Gender.WOMAN)
						);
					}
			);
		}
	}

	@Nested
	class ExceptionAssertions {

		@Test
		void exceptionTest() {
			Throwable exception = assertThrows(
					IllegalArgumentException.class, () -> Gender.valueOf("TEST")
			);
			assertEquals(exception.getMessage(), "No enum constant com.example.core.model.Gender.TEST");
		}
	}

	@Nested
	class FailAssertions {

		@Test
		void failTest() {
			fail("This is fail test.");
		}
	}

	@Nested
	class AssumptionAssertions {

		@Test
		void validTest() {
			assumeTrue("/Users/uchitate".equals(System.getenv("HOME")));
			assertTrue("test".length() == 4);
		}

		@Test
		void invalidTest() {
			assumeFalse("/Users/uchitate".equals(System.getenv("TEST")), () -> "This is invalid.");
		}

		@Test
		void executeTestWhenValid() {
			assumingThat("/Users/uchitate".equals(System.getenv("HOME")),
					() -> {
						assertFalse("test".length() == 2);
					});
			assertTrue("test".length() == 4);
		}
	}
}
