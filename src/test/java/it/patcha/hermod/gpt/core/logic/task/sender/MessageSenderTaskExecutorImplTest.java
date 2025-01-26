package it.patcha.hermod.gpt.core.logic.task.sender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MessageSenderTaskExecutorImplTest extends HermodBaseTest {

	@InjectMocks
	private MessageSenderTaskExecutorImpl taskExecutor;

	@Mock
	private ConnectionFactory connectionFactory;

	@Mock
	private Connection connection;

	@Mock
	private Session session;

	@Mock
	private Queue queue;

	@Mock
	private MessageProducer producer;

	@Mock
	private TextMessage message;

	@Mock
	private Appender<ILoggingEvent> logAppender;

	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		sendBean = new SendBean();
	}

	@Test
	void testCastToSendBean(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = taskExecutor.castToSendBean(sendBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			TaskExecutorException exception =
					assertThrows(TaskExecutorException.class, () -> taskExecutor.castToSendBean(new ArgsBean()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

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

			SendBean result = taskExecutor.readFile(sendBean);
			assertNotNull(result, getEndTestLogKO());
			swapInfoExpected(EXP_NULL.toString());
			assertNull(sendBean.getMessageFile(), getEndTestLogKO());
			assertNull(sendBean.getMessageText(), getEndTestLogKO());

			sendBean.setMessageFile(JMS_MESSAGE_FILE);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.readFile(JMS_MESSAGE_FILE)).thenReturn(JMS_MESSAGE_TEXT);

				swapInfoExpected(EXP_NOT_NULL.toString());
				result = taskExecutor.readFile(sendBean);
				assertNotNull(result, getEndTestLogKO());

				swapInfoExpected(JMS_MESSAGE_TEXT);
				assertEquals(JMS_MESSAGE_TEXT, result.getMessageText(), getEndTestLogKO());
				logger.debug("{}{}", getEndTestLogOK(), result.getMessageText());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSendMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			Logger rootLogger = (Logger) LoggerFactory.getLogger(MessageSenderTaskExecutorImpl.class);
			rootLogger.addAppender(logAppender);

			sendBean.setConnectionFactory(connectionFactory);
			sendBean.setRequestQueueName(REQUEST_QUEUE_NAME);
			sendBean.setMessageText(JMS_MESSAGE_TEXT);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.createConnection(connectionFactory)).thenReturn(connection);
				hermodUtils.when(() -> HermodUtils.createSession(connection)).thenReturn(session);

				doReturn(queue).when(session).createQueue(sendBean.getRequestQueueName());
				doReturn(producer).when(session).createProducer(queue);
				doReturn(message).when(session).createTextMessage(sendBean.getMessageText());
				doNothing().when(producer).send(message);

				SendBean result = taskExecutor.sendMessage(sendBean);
				boolean check = checkIntoLogs(
						HermodConstants.MESSAGE_SENT_LOG.replace("{}", sendBean.getRequestQueueName()), Level.INFO, logAppender);
				assertTrue(check, getEndTestLogKO());

				swapInfoExpected(EXP_NOT_NULL.toString());
				assertNotNull(result, getEndTestLogKO());

				swapInfoExpected(EXP_TRUE.toString());
				assertTrue(sendBean.isSuccessful(), getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), check);
			}

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			TaskExecutorException exception =
					assertThrows(TaskExecutorException.class, () -> taskExecutor.sendMessage(new SendBean()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}