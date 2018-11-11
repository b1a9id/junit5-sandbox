import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

class ConditionTest {

    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMac() {
        System.out.println("This is executed test only mac");
    }

    @Test
    @DisabledOnOs(OS.MAC)
    void disabledOnlyMac() {
        System.out.println("This is disabled test only mac");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void onlyOnJava8() {
        System.out.println("This is executed test only java8");
    }

    @Test
    @DisabledOnJre({JRE.JAVA_8, JRE.JAVA_9})
    void disabledOnJava8AndJava9() {
        System.out.println("This is disabled test java8 and java9");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "HOME", matches = "/Users/uchitate")
    void onlyOnHomeMatch() {
        System.out.println("This is executed test on Home is /Users/uchitate");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "HOME", matches = "/Users/uchitate")
    void disabledOnHomeMatch() {
        System.out.println("This is disabled test on Home is /Users/uchitate");
    }
}
