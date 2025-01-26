package it.patcha.hermod.gpt.common.util;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.UnformattedHermodException;
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

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_CONTAINS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.RE99;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodUtilsTest extends HermodBaseTest {

	private static final String JMS_WRONG_FILE_PATH = "wrong.test";
	private static final String ERROR_CODE_2 = RE99.getCode();

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
			assertNotNull(result, getEndTestLogKO());

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

			ConnectionFactory result = HermodUtils.getConnectionFactory(CONNECTION_FACTORY_URL);
			assertNotNull(result, getEndTestLogKO());

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
			assertNotNull(result, getEndTestLogKO());

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
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatIntoHermodException(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.formatIntoHermodException(ERROR_THROWABLE, HermodException.class, this.getClass());
			assertNotNull(result, getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.formatIntoHermodException(ERROR_THROWABLE, HermodException.class, this.getClass(), ERROR_CODE);
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.formatIntoHermodException(ERROR_CAUSE, HermodException.class, this.getClass());
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.formatIntoHermodException(ERROR_CAUSE, HermodException.class, this.getClass(), ERROR_CODE_2);
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE_2);
			assertEquals(ERROR_CODE_2, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeIntoHermodException(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = HermodUtils.includeIntoHermodException(ERROR_THROWABLE, HermodException.class);
			assertNotNull(result, getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.includeIntoHermodException(ERROR_THROWABLE, HermodException.class, ERROR_CODE);
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.includeIntoHermodException(ERROR_CAUSE, HermodException.class);
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			swapInfoExpected(EXP_NOT_NULL.toString());
			result = HermodUtils.includeIntoHermodException(ERROR_CAUSE, HermodException.class, ERROR_CODE_2);
			assertNotNull(result, getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_KO));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE_2);
			assertEquals(ERROR_CODE_2, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			File file = new File(getClass().getClassLoader().getResource(JMS_MESSAGE_FILE_PATH).getFile());
			String filePath = file.getPath();

			String result = HermodUtils.readFile(filePath);
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(JMS_MESSAGE_TEXT);
			assertEquals(JMS_MESSAGE_TEXT, result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EMPTY);
			result = HermodUtils.readFile(EMPTY);
			assertNull(result, getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + UnformattedHermodException.class.getName());
			UnformattedHermodException exception =
					assertThrows(UnformattedHermodException.class, () -> HermodUtils.readFile(JMS_WRONG_FILE_PATH), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}