package it.patcha.hermod.gpt.core.logic.task.sender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.error.UnformattedHermodException;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.apache.commons.lang3.StringUtils;
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

import java.io.File;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.TT01;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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
	void testCastToSendBean_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = taskExecutor.castToSendBean(sendBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testCastToSendBean_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			logger.debug(getStartTestLog());

			TaskExecutorException exception =
					assertThrowsToLog(TaskExecutorException.class, () -> taskExecutor.castToSendBean(new ArgsBean()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

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

			sendBean.setMessageFile(objJmsMessageFile);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.readFile(objJmsMessageFile)).thenReturn(valJmsMessageText);

				SendBean result = taskExecutor.readFile(sendBean);
				assertNotNullToLog(result, getEndTestLogKO());

				swapInfoExpected(valJmsMessageText);
				assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), result.getMessageText());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Empty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			sendBean.setMessageFile(new File(""));
			SendBean result = taskExecutor.readFile(sendBean);
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(sendBean.getMessageFile(), getEndTestLogKO());

			swapInfoExpected(EXP_TRUE.toString());
			assertTrueToLog(StringUtils.isEmpty(sendBean.getMessageFile().getPath()), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(sendBean.getMessageText(), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result.getMessageText());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Null(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			SendBean result = taskExecutor.readFile(sendBean);
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNullToLog(sendBean.getMessageFile(), getEndTestLogKO());
			assertNullToLog(sendBean.getMessageText(), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result.getMessageText());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Exception_Message(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			logger.debug(getStartTestLog());

			sendBean.setMessageFile(objJmsMessageFile);
			sendBean.setMessageText(valJmsMessageText);
			UnformattedHermodException unformattedHermodException = new UnformattedHermodException(TT01);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.readFile(objJmsMessageFile)).thenThrow(unformattedHermodException);

				SendBean result = taskExecutor.readFile(sendBean);
				assertNotNullToLog(result, getEndTestLogKO());

				swapInfoExpected(valJmsMessageText);
				assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), result.getMessageText());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testReadFile_Exception_NoMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			logger.debug(getStartTestLog());

			sendBean.setMessageFile(objJmsMessageFile);
			UnformattedHermodException unformattedHermodException = new UnformattedHermodException(TT01);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.readFile(objJmsMessageFile)).thenThrow(unformattedHermodException);
				hermodUtils.when(() -> HermodUtils.formatIntoHermodException(eq(unformattedHermodException), eq(TaskExecutorException.class), any(), isNull()))
						.thenReturn(new TaskExecutorException(TT01, unformattedHermodException));

				TaskExecutorException exception =
						assertThrowsToLog(TaskExecutorException.class, () -> taskExecutor.readFile(sendBean), getEndTestLogKO());

				logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSendMessage_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			Logger rootLogger = (Logger) LoggerFactory.getLogger(MessageSenderTaskExecutorImpl.class);
			rootLogger.addAppender(logAppender);

			sendBean.setConnectionFactory(connectionFactory);
			sendBean.setRequestQueueName(valRequestQueueName);
			sendBean.setMessageText(valJmsMessageText);
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
				assertTrueToLog(check, getEndTestLogKO());

				swapInfoExpected(EXP_NOT_NULL.toString());
				assertNotNullToLog(result, getEndTestLogKO());

				swapInfoExpected(EXP_TRUE.toString());
				assertTrueToLog(sendBean.isSuccessful(), getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), check);
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSendMessage_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + TaskExecutorException.class.getName());
			logger.debug(getStartTestLog());

			TaskExecutorException exception =
					assertThrowsToLog(TaskExecutorException.class, () -> taskExecutor.sendMessage(new SendBean()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}