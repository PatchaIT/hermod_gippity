package it.patcha.hermod.gpt.common.bean.ui.input;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.Arrays;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ArgsBeanTest extends HermodBaseTest {

	private static final String TO_STRING_EMPTY = "[args=<null>,messageText=<null>,messageFile=<null>,requestQueueName=<null>,replyQueueName=<null>,connectionFactoryUrl=<null>,successful=false]";

	private String[] args = null;
	private ArgsBean argsBean;

	@BeforeEach
	void setUp() throws Exception {
		args = new String[]{
				ARG_CONNECTION_FACTORY_URL, CONNECTION_FACTORY_URL,
				ARG_REQUEST_QUEUE_NAME, REQUEST_QUEUE_NAME,
				ARG_JMS_MESSAGE_TEXT, JMS_MESSAGE_TEXT,
				ARG_JMS_MESSAGE_FILE_PATH, JMS_MESSAGE_FILE_PATH
		};

		argsBean = new ArgsBean();
	}

	@Test
	void testSetGetArgs(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setArgs(args);
			String[] result = argsBean.getArgs();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected("... \n" + Arrays.toString(args));
			assertEquals(args, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), "\n" + Arrays.toString(result));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetMessageText(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageText(JMS_MESSAGE_TEXT);
			String result = argsBean.getMessageText();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(JMS_MESSAGE_TEXT);
			assertEquals(JMS_MESSAGE_TEXT, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetMessageFile(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageFile(JMS_MESSAGE_FILE);
			File result = argsBean.getMessageFile();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(JMS_MESSAGE_FILE.toString());
			assertEquals(JMS_MESSAGE_FILE, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetMessageFilePath(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageFilePath(JMS_MESSAGE_FILE_PATH);
			File result = argsBean.getMessageFile();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(JMS_MESSAGE_FILE.toString());
			assertEquals(JMS_MESSAGE_FILE, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetRequestQueueName(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setRequestQueueName(REQUEST_QUEUE_NAME);
			String result = argsBean.getRequestQueueName();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(REQUEST_QUEUE_NAME);
			assertEquals(REQUEST_QUEUE_NAME, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetReplyQueueName(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setReplyQueueName(REPLY_QUEUE_NAME);
			String result = argsBean.getReplyQueueName();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(REPLY_QUEUE_NAME);
			assertEquals(REPLY_QUEUE_NAME, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetConnectionFactory(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setConnectionFactoryUrl(CONNECTION_FACTORY_URL);
			String result = argsBean.getConnectionFactoryUrl();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(CONNECTION_FACTORY_URL);
			assertEquals(CONNECTION_FACTORY_URL, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testEquals(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			boolean result = argsBean.equals(new ArgsBean());
			assertTrue(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			result = argsBean.equals(argsBean);
			assertTrue(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			swapInfoExpected(EXP_FALSE.toString());

			result = argsBean.equals(new SendBean());
			assertFalse(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			argsBean.setSuccessful(true);
			result = argsBean.equals(new ArgsBean());
			assertFalse(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHashCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			int result = argsBean.hashCode();
			int again = argsBean.hashCode();
			int another = new ArgsBean().hashCode();

			assertEquals(again, result, getEndTestLogKO());
			assertEquals(another, result, getEndTestLog(TEST_AND_KO));

			swapInfoExpected(Integer.toString(result));
			logger.debug("{}{}", getEndTestLogOK(), another);

			swapInfoExpected(EXP_FALSE.toString());

			argsBean.setSuccessful(true);
			result = argsBean.hashCode();
			assertNotEquals(again, result, getEndTestLogKO());

			swapInfoExpected("not " + again);
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = argsBean.toString();
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

			swapInfoExpected(EXP_TRUE.toString());
			assertTrue(result.contains(TO_STRING_EMPTY), getEndTestLog(TEST_AND_KO));

			swapInfoExpected("... \ncontains: " + TO_STRING_EMPTY);
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), "\n" + result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}