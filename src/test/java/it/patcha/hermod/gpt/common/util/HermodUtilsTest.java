package it.patcha.hermod.gpt.common.util;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.error.HermodException;
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

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodUtilsTest extends HermodBaseTest {

	private static final String JMS_WRONG_FILE_PATH = "wrong.test";

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
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug(getStartTestLog());

			HermodException exception =
					assertThrowsToLog(HermodException.class, () -> HermodUtils.readFile(JMS_WRONG_FILE_PATH), getEndTestLogKO());

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
		public NoSimpleConstructorTestHermodException(String message) {super(message);}
		public NoSimpleConstructorTestHermodException(ErrorType errorType, Throwable cause) {
			super(errorType.getMessage(), errorType.getCode(), cause);
		}
	}

}