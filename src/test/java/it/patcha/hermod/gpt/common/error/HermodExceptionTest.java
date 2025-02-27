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
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

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

			hermodException.setCode(errorCode);
			String result = hermodException.getCode();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result, getEndTestLog(TEST_AND_KO));

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

			hermodException.setMessage(errorMessage);
			String result = hermodException.getMessage();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result, getEndTestLog(TEST_AND_KO));

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

			hermodException.setCause(errorCause);
			Throwable result = hermodException.getCause();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + errorCause.getClass().getName());
			assertEqualsToLog(errorCause, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithChild(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException = new HermodException(errorCause);
			assertNotNullToLog(hermodException.getMessage(), getEndTestLogKO());
			assertNotNullToLog(hermodException.getCode(), getEndTestLogKO());
			assertNotNullToLog(hermodException.getCause(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, hermodException.toString());

			swapInfoExpected(errorCause.getMessage());
			assertEqualsToLog(errorCause.getMessage(), hermodException.getMessage(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(errorCause.getCode());
			assertEqualsToLog(errorCause.getCode(), hermodException.getCode(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_EXCEPTION + LOG_NL + errorCause.getClass().getName());
			assertEqualsToLog(errorCause, hermodException.getCause(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, hermodException.getCause().toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithThrowable(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException = new HermodException(errorThrowable);
			assertNotNullToLog(hermodException.getCause(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, hermodException.throwableToString());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(hermodException.getMessage(), getEndTestLogKO());
			assertNullToLog(hermodException.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + errorThrowable.getClass().getName());
			assertEqualsToLog(errorThrowable, hermodException.getCause(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, hermodException.getCause().toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorType(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException = new HermodException(errorType);
			assertNotNullToLog(hermodException.getMessage(), getEndTestLogKO());
			assertNotNullToLog(hermodException.getCode(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, hermodException.throwableToString());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(hermodException.getCause(), getEndTestLogKO());

			swapInfoExpected(errorType.getMessage());
			assertEqualsToLog(errorType.getMessage(), hermodException.getMessage(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(errorType.getCode());
			assertEqualsToLog(errorType.getCode(), hermodException.getCode(), getEndTestLog(TEST_AND_KO));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorTypeAndThrowable(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			hermodException = new HermodException(errorType, errorThrowable);
			assertNotNullToLog(hermodException.getMessage(), getEndTestLogKO());
			assertNotNullToLog(hermodException.getCode(), getEndTestLogKO());
			assertNotNullToLog(hermodException.getCause(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, hermodException.throwableToString());

			swapInfoExpected(errorType.getMessage());
			assertEqualsToLog(errorType.getMessage(), hermodException.getMessage(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(errorType.getCode());
			assertEqualsToLog(errorType.getCode(), hermodException.getCode(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_EXCEPTION + LOG_NL + errorThrowable.getClass().getName());
			assertEqualsToLog(errorThrowable, hermodException.getCause(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, hermodException.getCause().toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}