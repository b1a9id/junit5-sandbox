package argumentprovider;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.example.core.model.Gender;
import com.example.core.model.Person;

public class MyArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Person personA = new Person("Ryosuke", "Uchitate", 27, Gender.MAN);
        Person personB = new Person("Taro", "Uchitate", 20, Gender.MAN);
        return Stream.of(personA, personB).map(Arguments::of);
    }
}
