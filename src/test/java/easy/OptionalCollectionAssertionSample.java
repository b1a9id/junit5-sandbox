package easy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.core.model.Person;

import static com.example.core.model.Gender.MAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OptionalCollectionAssertionSample {

    @Test
    void optionalAssertion() {
        Optional<Person> personOptional =
                Optional.of(new Person("Ryosuke", "Uchitate", 27, MAN));
        Person person = personOptional.orElse(null);
        assertNotNull(person);
        assertEquals("Ryosuke", person.getFirstName());
        assertEquals("Uchitate", person.getLastName());
        assertEquals(27, person.getAge());
        assertEquals(MAN, person.getGender());
    }

    @Test
    void collectionAssertion() {
        List<String> list = Arrays.asList("TEST1", "TEST2");
        assertEquals("TEST1", list.get(0));
        assertEquals("TEST2", list.get(1));
    }
}
