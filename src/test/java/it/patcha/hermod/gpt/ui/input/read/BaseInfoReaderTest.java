package it.patcha.hermod.gpt.ui.input.read;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;
import org.junit.jupiter.api.BeforeEach;
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
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class BaseInfoReaderTest extends HermodBaseTest {

	@InjectMocks
	private BaseInfoReaderTest.BaseInfoReaderImpl infoReader;

	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();
	}

	@Test
	void testHandleOptions_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodBean result = infoReader.handleOptions(argsBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalseToLog(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHandleOptions_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + InfoReaderException.class.getName());
			logger.debug(getStartTestLog());

			InfoReaderException exception =
					assertThrowsToLog(InfoReaderException.class, () -> infoReader.handleOptions(null), getEndTestLogKO());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatInfoReaderException_Message_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException(errorMessage);
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
	void testFormatInfoReaderException_MessageCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException(errorMessage, errorCode);
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
	void testFormatInfoReaderException_MessageCause_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException(errorMessage, errorCause);
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
	void testFormatInfoReaderException_Type_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException(errorType);
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
	void testFormatInfoReaderException_TypeCause_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException(errorType, errorCause);
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
	void testFormatInfoReaderException_TypeCause_Null(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.formatInfoReaderException((ErrorType) null, errorCause);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + InfoReaderException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeAndFormatIntoInfoReaderException_Throwable(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.includeAndFormatIntoInfoReaderException(errorThrowable);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLog(TEST_AND_OK));

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
	void testIncludeAndFormatIntoInfoReaderException_ThrowableCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			InfoReaderException result = infoReader.includeAndFormatIntoInfoReaderException(errorThrowable, errorCode);
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
	void testIncludeAndFormatIntoInfoReaderException_ThrowableCode_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(
								() -> HermodUtils.formatIntoHermodException(eq(errorThrowable), eq(InfoReaderException.class), any(), eq(errorCode)))
						.thenThrow(errorCause);

				InfoReaderException result = infoReader.includeAndFormatIntoInfoReaderException(errorThrowable, errorCode);
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

	/** An extension of BaseInfoReader used only for test purposes */
	private static class BaseInfoReaderImpl extends BaseInfoReader {
	}

}