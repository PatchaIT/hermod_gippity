package it.patcha.hermod.gpt.common.bean.core.logic;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import jakarta.jms.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class SendBeanTest extends HermodBaseTest {

	private static final String TO_STRING_EMPTY = "[messageText=<null>,messageFile=<null>,requestQueueName=<null>,replyQueueName=<null>,connectionFactory=<null>,successful=false]";

	@Mock
	private ConnectionFactory connectionFactory;

	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		sendBean = new SendBean();
	}

	@Test
	void testSetGetMessageText(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			sendBean.setMessageText(JMS_MESSAGE_TEXT);
			String result = sendBean.getMessageText();
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

			sendBean.setMessageFile(JMS_MESSAGE_FILE);
			File result = sendBean.getMessageFile();
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

			sendBean.setMessageFilePath(JMS_MESSAGE_FILE_PATH);
			File result = sendBean.getMessageFile();
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

			sendBean.setRequestQueueName(REQUEST_QUEUE_NAME);
			String result = sendBean.getRequestQueueName();
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

			sendBean.setReplyQueueName(REPLY_QUEUE_NAME);
			String result = sendBean.getReplyQueueName();
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

			sendBean.setConnectionFactory(connectionFactory);
			ConnectionFactory result = sendBean.getConnectionFactory();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(connectionFactory.toString());
			assertEquals(connectionFactory, result, getEndTestLog(TEST_AND_KO));

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

			boolean result = sendBean.equals(new SendBean());
			assertTrue(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			result = sendBean.equals(sendBean);
			assertTrue(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			swapInfoExpected(EXP_FALSE.toString());

			result = sendBean.equals(new ArgsBean());
			assertFalse(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			sendBean.setSuccessful(true);
			result = sendBean.equals(new SendBean());
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

			int result = sendBean.hashCode();
			int again = sendBean.hashCode();
			int another = new SendBean().hashCode();

			assertEquals(again, result, getEndTestLogKO());
			assertEquals(another, result, getEndTestLog(TEST_AND_KO));

			swapInfoExpected(Integer.toString(result));
			logger.debug("{}{}", getEndTestLogOK(), another);

			swapInfoExpected(EXP_FALSE.toString());

			sendBean.setSuccessful(true);
			result = sendBean.hashCode();
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

			String result = sendBean.toString();
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

			swapInfoExpected(EXP_TRUE.toString());
			assertTrue(result.contains(TO_STRING_EMPTY), getEndTestLog(TEST_AND_KO));

			swapInfoExpected("... \ncontains: " + TO_STRING_EMPTY);
			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLog(TEST_AND_OK), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}