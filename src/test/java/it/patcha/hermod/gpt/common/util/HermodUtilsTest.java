package it.patcha.hermod.gpt.common.util;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.UnformattedHermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.config.SpringConfig;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.Serial;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_CONTAINS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.UNKNOWN_CLASS;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.RE99;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodUtilsTest extends HermodBaseTest {

	private static final String JMS_WRONG_FILE_PATH = "wrong.test";
	private static final String ERROR_CODE_RE99 = RE99.getCode();

	@Mock
	private ConnectionFactory connectionFactory;

	@Mock
	private Connection connection;

	@Mock
	private Session session;

	@Test
	void testGetContext(TestInfo testInfo) {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ApplicationContext result = HermodUtils.getContext(SpringConfig.class);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetConnectionFactory(TestInfo testInfo) {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ConnectionFactory result = HermodUtils.getConnectionFactory(valConnectionFactoryUrl);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testCreateConnection(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(connection).when(connectionFactory).createConnection();

			Connection result = HermodUtils.createConnection(connectionFactory);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testCreateSession(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(session).when(connection).createSession(false, Session.AUTO_ACKNOWLEDGE);

			Session result = HermodUtils.createSession(connection);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatIntoHermodException_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(errorCause, HermodException.class, this.getClass(), ERROR_CODE_RE99);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE_RE99);
			assertEqualsToLog(ERROR_CODE_RE99, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatIntoHermodException_NoCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(errorCause, HermodException.class, this.getClass());
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

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
	void testFormatIntoHermodException_RuntimeExc_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(errorThrowable, HermodException.class, this.getClass(), errorCode);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLogKO());

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
	void testFormatIntoHermodException_RuntimeExc_NoCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(errorThrowable, HermodException.class, this.getClass());
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatIntoHermodException_NullClass_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(errorCause, HermodException.class, null, ERROR_CODE_RE99);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE_RE99);
			assertEqualsToLog(ERROR_CODE_RE99, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(result.getMessage().contains(errorMessage), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + UNKNOWN_CLASS);
			assertTrueToLog(result.getMessage().contains(UNKNOWN_CLASS), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatIntoHermodException_ReflectiveOperationException_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException exception =
					assertThrowsToLog(HermodException.class,
							() -> HermodUtils.formatIntoHermodException(errorCause, NoSimpleConstructorTestHermodException.class, this.getClass(), ERROR_CODE_RE99),
							getEndTestLogKO());

			assertNotNullToLog(exception, getEndTestLogKO());
			assertNotNullToLog(exception.getMessage(), getEndTestLogKO());
			assertNotNullToLog(exception.getCause(), getEndTestLogKO());
			assertNotNullToLog(exception.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, exception.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE_RE99);
			assertEqualsToLog(ERROR_CODE_RE99, exception.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + errorMessage);
			assertTrueToLog(exception.getMessage().contains(errorMessage), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatHermodException_Null_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatHermodException(null, null, null, null, null);
			assertNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result != null ? result.toString() : result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(errorCause, HermodException.class, ERROR_CODE_RE99);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE_RE99);
			assertEqualsToLog(ERROR_CODE_RE99, result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_NoCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(errorCause, HermodException.class);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_RuntimeExc_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(errorThrowable, HermodException.class, errorCode);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_RuntimeExc_NoCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(errorThrowable, HermodException.class);
			assertNotNullToLog(result, getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_ReflectiveOperationException_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException exception =
					assertThrowsToLog(HermodException.class,
							() -> HermodUtils.includeIntoHermodException(errorCause, NoSimpleConstructorTestHermodException.class, ERROR_CODE_RE99),
							getEndTestLogKO());

			assertNotNullToLog(exception, getEndTestLogKO());
			assertNotNullToLog(exception.getMessage(), getEndTestLogKO());
			assertNotNullToLog(exception.getCause(), getEndTestLogKO());
			assertNotNullToLog(exception.getCode(), getEndTestLogKO());

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, exception.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE_RE99);
			assertEqualsToLog(ERROR_CODE_RE99, exception.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, exception.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException_NullException_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(errorCause, null, ERROR_CODE_RE99);
			assertNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result != null ? result.toString() : result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			File file = new File(getClass().getClassLoader().getResource(valJmsMessageFilePath).getFile());
			String filePath = file.getPath();

			String result = HermodUtils.readFile(filePath);
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Empty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			String result = HermodUtils.readFile((File) null);
			assertNullToLog(result, getEndTestLogKO());

			result = HermodUtils.readFile(empty);
			assertNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + UnformattedHermodException.class.getName());
			logger.debug(getStartTestLog());

			UnformattedHermodException exception =
					assertThrowsToLog(UnformattedHermodException.class, () -> HermodUtils.readFile(JMS_WRONG_FILE_PATH), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetSimpleName_Class(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = HermodUtils.getSimpleName(errorCause.getClass());
			assertNotNullToLog(result, getEndTestLogKO());

			String actual = errorCause.getClass().getSimpleName();
			swapInfoExpected(actual);
			assertEqualsToLog(actual, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetSimpleName_Instance(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = HermodUtils.getSimpleName(errorCause);
			assertNotNullToLog(result, getEndTestLogKO());

			String actual = errorCause.getClass().getSimpleName();
			swapInfoExpected(actual);
			assertEqualsToLog(actual, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetClassName_Class(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = HermodUtils.getClassName(errorCause.getClass());
			assertNotNullToLog(result, getEndTestLogKO());

			String actual = errorCause.getClass().getName();
			swapInfoExpected(LOG_NL + actual);
			assertEqualsToLog(actual, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetClassName_Instance(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = HermodUtils.getClassName(errorCause);
			assertNotNullToLog(result, getEndTestLogKO());

			String actual = errorCause.getClass().getName();
			swapInfoExpected(LOG_NL + actual);
			assertEqualsToLog(actual, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of HermodException used only for test purposes */
	public static class NoSimpleConstructorTestHermodException extends HermodException {
		@Serial
		private static final long serialVersionUID = 7295198888273346093L;
		private NoSimpleConstructorTestHermodException() {}
		public NoSimpleConstructorTestHermodException(String message) {super(message);}
		public NoSimpleConstructorTestHermodException(ErrorType errorType, Throwable cause) {
			super(errorType.getMessage(), errorType.getCode(), cause);
		}
	}

}