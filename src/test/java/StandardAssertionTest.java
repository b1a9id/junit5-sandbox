import com.example.core.model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

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
			assertAll("name",
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
		}

		@Test
		void invalidTest() {
			assumeFalse("/Users/uchitate".equals(System.getenv("HOME")), () -> "This is invalid.");
		}

		@Test
		void executeTestWhenValid() {
			assumeTrue("/Users/uchitate".equals(System.getenv("HOME")));
			assertTrue("test".length() == 4);
		}
	}
}
