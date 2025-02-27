package it.patcha.hermod.gpt.common.bean.core.logic;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
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
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class SendBeanTest extends HermodBaseTest {

	private static final String TO_STRING_EMPTY = "[messageText=<null>,messageFile=<null>,requestQueueName=<null>,replyQueueName=<null>,connectionFactory=<null>,successful=false]";
	private static final String EXP_MESSAGE_TEXT = "messageText=";
	private static final String EXP_MESSAGE_FILE = "messageFile=";

	@Mock
	private ConnectionFactory connectionFactory;

	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		sendBean = new SendBean();
	}

	@Test
	void testConstructorFullFile(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = new SendBean(valJmsMessageText, objJmsMessageFile, valRequestQueueName, objConnectionFactory);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(valJmsMessageFilePath);
			assertEqualsToLog(valJmsMessageFilePath, result.getMessageFile().getPath(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result.getRequestQueueName(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, ((ActiveMQConnectionFactory) result.getConnectionFactory()).getBrokerURL(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_MESSAGE_TEXT + valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorFullString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = new SendBean(valJmsMessageText, valJmsMessageFilePath, valRequestQueueName, objConnectionFactory);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(valJmsMessageFilePath);
			assertEqualsToLog(valJmsMessageFilePath, result.getMessageFile().getPath(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result.getRequestQueueName(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, ((ActiveMQConnectionFactory) result.getConnectionFactory()).getBrokerURL(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_MESSAGE_TEXT + valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorFile(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = new SendBean(objJmsMessageFile, valRequestQueueName, objConnectionFactory);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getMessageText(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result.getRequestQueueName(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, ((ActiveMQConnectionFactory) result.getConnectionFactory()).getBrokerURL(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_MESSAGE_FILE + valJmsMessageFilePath);
			assertEqualsToLog(valJmsMessageFilePath, result.getMessageFile().getPath(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = new SendBean(valJmsMessageText, valRequestQueueName, objConnectionFactory);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(result.getMessageFile(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result.getRequestQueueName(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, ((ActiveMQConnectionFactory) result.getConnectionFactory()).getBrokerURL(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_MESSAGE_TEXT + valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

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

			sendBean.setMessageText(valJmsMessageText);
			String result = this.sendBean.getMessageText();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result, getEndTestLog(TEST_AND_KO));

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

			sendBean.setMessageFile(objJmsMessageFile);
			File result = this.sendBean.getMessageFile();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(objJmsMessageFile.toString());
			assertEqualsToLog(objJmsMessageFile, result, getEndTestLog(TEST_AND_KO));

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

			sendBean.setMessageFilePath(valJmsMessageFilePath);
			File result = this.sendBean.getMessageFile();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(objJmsMessageFile.toString());
			assertEqualsToLog(objJmsMessageFile, result, getEndTestLog(TEST_AND_KO));

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

			sendBean.setRequestQueueName(valRequestQueueName);
			String result = this.sendBean.getRequestQueueName();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result, getEndTestLog(TEST_AND_KO));

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

			sendBean.setReplyQueueName(valReplyQueueName);
			String result = this.sendBean.getReplyQueueName();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valReplyQueueName);
			assertEqualsToLog(valReplyQueueName, result, getEndTestLog(TEST_AND_KO));

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
			ConnectionFactory result = this.sendBean.getConnectionFactory();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(connectionFactory.toString());
			assertEqualsToLog(connectionFactory, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testEquals_True(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			boolean result = this.sendBean.equals(new SendBean());
			assertTrueToLog(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			result = this.sendBean.equals(this.sendBean);
			assertTrueToLog(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testEquals_False(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_FALSE.toString());
			logger.debug(getStartTestLog());

			boolean result = this.sendBean.equals(new ArgsBean());
			assertFalseToLog(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			this.sendBean.setSuccessful(true);
			result = this.sendBean.equals(new SendBean());
			assertFalseToLog(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHashCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			int result = this.sendBean.hashCode();
			int again = this.sendBean.hashCode();
			int another = new SendBean().hashCode();

			assertEqualsToLog(again, result, getEndTestLogKO());
			assertEqualsToLog(another, result, getEndTestLog(TEST_AND_KO));

			swapInfoExpected(Integer.toString(result));
			logger.debug("{}{}", getEndTestLogOK(), another);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHashCode_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			int current = sendBean.hashCode();

			sendBean.setSuccessful(true);
			int result = this.sendBean.hashCode();
			assertNotEqualsToLog(current, result, getEndTestLogKO());

			swapInfoExpected("not " + current);
			logger.debug("{}{}", getEndTestLogOK(), result);

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

			String result = this.sendBean.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

			swapInfoExpected(EXP_TRUE.toString());
			assertTrueToLog(result.contains(TO_STRING_EMPTY), getEndTestLog(TEST_AND_KO));

			swapInfoExpected("... \ncontains: " + TO_STRING_EMPTY);
			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLog(TEST_AND_OK), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}