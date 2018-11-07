package easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.core.model.Gender;
import com.example.core.model.Person;
import com.sun.xml.internal.ws.developer.UsesJAXBContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssertionsSample {

    @Test
    void standardAssertions() {
        assertEquals(2, 2);
        assertEquals(1, 1, "failed msg.");
    }

    @Test
    void groupedAssertions() {
        Person person = new Person("Ryosuke", "Uchitate", 28, Gender.MAN);
        assertAll("failed msg",
                () -> assertEquals("Ryosuke", person.getFirstName()),
                () -> assertEquals(28, person.getAge()));
    }

    @Test
    void dependentAssertions() {
        Person person = new Person("Ryosuke", "Uchitate", 28, Gender.MAN);
        assertAll("failed msg1",
                () -> {
                    assertNotNull(person.getFirstName());

                    assertAll("failed msg2",
                            () -> assertTrue(person.getFirstName().startsWith("R")),
                            () -> assertFalse(person.getLastName().startsWith("T")));
                },
                () -> {
                    assertNotNull(person.getLastName());

                    assertAll("failed msg3",
                            () -> assertFalse(person.getFirstName().startsWith("A")),
                            () -> assertTrue(person.getLastName().startsWith("U")));
                });
    }

    @Test
    void exceptionTesting() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("exception test"); });
        assertEquals("exception test", exception.getMessage());
    }
}
