package it.patcha.hermod.gpt.core.logic.task.sender;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.error.UnformattedHermodException;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.core.logic.task.BaseTaskExecutor;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.WM02;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.WM03;

/** Implementation of MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
@Component
public class MessageSenderTaskExecutorImpl extends BaseTaskExecutor implements MessageSenderTaskExecutor {

	@Override
	public SendBean castToSendBean(HermodBean hermodBean) throws TaskExecutorException {
		if (hermodBean instanceof SendBean)
			return (SendBean) hermodBean;

		else
			throw formatTaskExecutorException(
					String.format(
							WM02.getMessage(), getSimpleName(SendBean.class), getSimpleName(hermodBean)),
					WM02.getCode());
	}

	@Override
	public SendBean readFile(SendBean sendBean) throws TaskExecutorException {
		File file = sendBean.getMessageFile();
		if (file != null && !StringUtils.isEmpty(file.getPath())) {
			try {
				sendBean.setMessageText(HermodUtils.readFile(sendBean.getMessageFile()));

			} catch (UnformattedHermodException e) {
				if (StringUtils.isEmpty(sendBean.getMessageText()))
					throw includeAndFormatIntoTaskExecutorException(e);
			}
		}

		return sendBean;
	}

	@Override
	public SendBean sendMessage(SendBean sendBean) throws TaskExecutorException {
		ConnectionFactory connectionFactory = sendBean.getConnectionFactory();
		String requestQueueName = sendBean.getRequestQueueName();
		String messageText = sendBean.getMessageText();

		try (Connection connection = HermodUtils.createConnection(connectionFactory);
			 Session session = HermodUtils.createSession(connection)) {

			connection.start();

			Queue queue = session.createQueue(requestQueueName);
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage(messageText);
			producer.send(message);

			sendBean.setSuccessful(true);
			logger.info(HermodConstants.MESSAGE_SENT_LOG, requestQueueName);

		} catch (Exception e) {
			throw formatTaskExecutorException(String.format(WM03.getMessage(), requestQueueName), WM03.getCode(), e);
		}

		return sendBean;
	}

}
