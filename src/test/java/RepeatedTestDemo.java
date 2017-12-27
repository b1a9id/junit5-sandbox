import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RepeatedTestDemo {

	@BeforeEach
	void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
		int currentRepetition = repetitionInfo.getCurrentRepetition();
		int totalRepetitions = repetitionInfo.getTotalRepetitions();
		String testClass = testInfo.getTestClass().get().getName();
		String methodName = testInfo.getTestMethod().get().getName();

		System.out.println(String.format("About to execute repetition %d of %d for %s#%s", //
				currentRepetition, totalRepetitions, testClass, methodName));
	}

	@RepeatedTest(3)
	void noArgs() {
		assertTrue("test".length() == 4);
	}

	@RepeatedTest(3)
	void totalRepetitionsInfo(RepetitionInfo repetitionInfo) {
		assertEquals(3, repetitionInfo.getTotalRepetitions());
	}

	@RepeatedTest(value = 3, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	@DisplayName("Custom Repeated Test!!")
	void customDisplayName(TestInfo testInfo, RepetitionInfo repetitionInfo) {
		int currentRepetition = repetitionInfo.getCurrentRepetition();
		int totalRepetitions = repetitionInfo.getTotalRepetitions();
		assertEquals(String.format("Custom Repeated Test!! %d/%d", currentRepetition, totalRepetitions), testInfo.getDisplayName());
	}

	@RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
	@DisplayName("Details...")
	void customDisplayNameLongPattern(TestInfo testInfo) {
		assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
	}
}
