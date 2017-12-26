import org.junit.jupiter.api.*;

@DisplayName("This is standard test.")
class StandardTest {

	@BeforeAll
	static void beforeAll() {
		System.out.println("executed before all test just once.");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("executed before each test.");
	}

	@Test
	@DisplayName("\uD83C\uDFC0")
	void test1() {
		System.out.println("This is test1.");
	}

	@Test
	@Disabled("This is Disabled test.")
	void test2() {
		System.out.println("This is test2.");
	}

	@Test
	void test3() {
		System.out.println("This is test3.");
	}
}
