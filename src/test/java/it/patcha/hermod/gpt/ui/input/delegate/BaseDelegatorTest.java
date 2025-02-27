package it.patcha.hermod.gpt.ui.input.delegate;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_CONTAINS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class BaseDelegatorTest extends HermodBaseTest {

	@InjectMocks
	private BaseDelegatorImpl delegator;

	@Test
	void testFormatDelegatorException_Message_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException(errorMessage);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatDelegatorException_MessageCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException(errorMessage, errorCode);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatDelegatorException_MessageCause_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException(errorMessage, errorCause);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatDelegatorException_Type_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException(errorType);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorType.getMessage());
			assertTrueToLog(result.getMessage().contains(errorType.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatDelegatorException_TypeCause_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException(errorType, errorCause);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorType.getMessage());
			assertTrueToLog(result.getMessage().contains(errorType.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatDelegatorException_TypeCause_Null(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.formatDelegatorException((ErrorType) null, errorCause);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeAndFormatIntoDelegatorException_Throwable(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.includeAndFormatIntoDelegatorException(errorThrowable);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + errorThrowable.getMessage());
			assertTrueToLog(result.getMessage().contains(errorThrowable.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeAndFormatIntoDelegatorException_ThrowableCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			DelegatorException result = delegator.includeAndFormatIntoDelegatorException(errorThrowable, errorCode);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorThrowable.getMessage());
			assertTrueToLog(result.getMessage().contains(errorThrowable.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeAndFormatIntoDelegatorException_ThrowableCode_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(
						() -> HermodUtils.formatIntoHermodException(eq(errorThrowable), eq(DelegatorException.class), any(), eq(errorCode)))
						.thenThrow(errorCause);

				DelegatorException result = delegator.includeAndFormatIntoDelegatorException(errorThrowable, errorCode);
				assertNotNullToLog(result, getEndTestLogKO());
				assertNotNullToLog(result.getMessage(), getEndTestLogKO());
				assertNotNullToLog(result.getCause(), getEndTestLogKO());
				assertNotNullToLog(result.getCode(), getEndTestLogKO());

				swapInfoExpected(errorCause.toString());
				assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

				swapInfoExpected(errorCode);
				assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

				swapInfoExpected(EXP_CONTAINS + errorThrowable.getMessage());
				assertTrueToLog(result.getMessage().contains(errorThrowable.getMessage()), getEndTestLogKO());

				logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of BaseDelegator used only for test purposes */
	private static class BaseDelegatorImpl extends BaseDelegator {
	}

}