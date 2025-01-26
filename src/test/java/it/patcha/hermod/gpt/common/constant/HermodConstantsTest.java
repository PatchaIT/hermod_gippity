package it.patcha.hermod.gpt.common.constant;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.constant.HermodConstants.BackgroundColor;
import it.patcha.hermod.gpt.common.constant.HermodConstants.TextColor;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.RESET_ALL;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodConstantsTest extends HermodBaseTest {

	private static final String BLUE_TXT = "\u001B[34m";
	private static final String BLUE_BKG = "\u001B[44m";
	private static final String SAMPLE_TEXT = "this color" + RESET_ALL;

	@Test
	void testTextColorToString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = TextColor.BLUE.toString();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(BLUE_TXT + SAMPLE_TEXT);
			assertEquals(BLUE_TXT, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result + SAMPLE_TEXT);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testBackgroundColorToString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = BackgroundColor.BLUE.toString();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(BLUE_BKG + SAMPLE_TEXT);
			assertEquals(BLUE_BKG, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result + SAMPLE_TEXT);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}