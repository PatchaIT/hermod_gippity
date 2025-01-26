package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodExceptionTest extends HermodBaseTest {

	@InjectMocks
	private HermodException hermodException;

	@BeforeEach
	void setUp() {
		hermodException = new HermodException();
	}

	@Test
	void testSetGetCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException.setCode(ERROR_CODE);
			String result = hermodException.getCode();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException.setMessage(ERROR_MESSAGE);
			String result = hermodException.getMessage();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException.setCause(ERROR_CAUSE);
			Throwable result = hermodException.getCause();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ERROR_CAUSE.getClass().getName());
			assertEquals(ERROR_CAUSE, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}
}