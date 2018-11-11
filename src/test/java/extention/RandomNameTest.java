package extention;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import extention.RandomNameExtension.RandomName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(RandomNameExtension.class)
class RandomNameTest {
    @Test
    void randomName(@RandomName String name) {
        System.out.println(name);
        assertEquals(4, name.length());
    }
}
