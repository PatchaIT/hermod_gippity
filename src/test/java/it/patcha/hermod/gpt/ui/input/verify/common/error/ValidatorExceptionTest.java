package it.patcha.hermod.gpt.ui.input.verify.common.error;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ValidatorExceptionTest extends HermodBaseTest {

	@Test
	void testConstructorEmpty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException();
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorChild(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorMessage);
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorMessage, errorCause);
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorMessage, errorCode);
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCodeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorMessage, errorCode, errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorErrorType(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorType);
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorErrorTypeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorType, errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			ValidatorException result = new ValidatorException(errorThrowable);
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}