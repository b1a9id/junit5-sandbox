import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("TestInfo Demo")
class TestInfoDemo {

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("Before Eash : " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("TEST1")
    void test1(TestInfo testInfo) {
        assertEquals("TEST1", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("TEST2")
    void test2(TestInfo testInfo) {
        assertEquals("TEST2", testInfo.getDisplayName());
    }
}
