package it.patcha.hermod.gpt.core.logic.flow.sender;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.core.logic.flow.BaseWorkflowManager;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

/** TODO: This class is not here to stay, it just keeps old and new codes I'll need later */
public class MessageSenderWithReplyWorkflowManagerImpl extends BaseWorkflowManager implements MessageSenderWorkflowManager {

	@Override
	public SendBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException {
		// TODO: This class is not here to stay, it just keeps old and new codes I'll need later
		SendBean sendBean = (SendBean) super.handleWorkflow(workflowInput);
		ConnectionFactory connectionFactory = sendBean.getConnectionFactory();
		String requestQueueName = sendBean.getRequestQueueName();
		String messageText = sendBean.getMessageText();
		String replyQueueName = sendBean.getReplyQueueName();

		try (Connection connection = connectionFactory.createConnection();
			 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

			connection.start();

			// Crea la coda di richiesta
			Queue requestQueue = session.createQueue(requestQueueName);
			MessageProducer producer = session.createProducer(requestQueue);
			TextMessage message = session.createTextMessage(messageText);

			// Invia il messaggio
			producer.send(message);

			sendBean.setSuccessful(true);
			logger.info("Message sent to queue: {}", requestQueueName);

			// Se è stata specificata una coda di risposta, gestisci la risposta
			if (replyQueueName != null && !replyQueueName.isEmpty()) {
				Queue replyQueue = session.createQueue(replyQueueName);
				message.setJMSReplyTo(replyQueue);

				// Aspetta la risposta, con un timeout (ad esempio 5 secondi)
				MessageConsumer consumer = session.createConsumer(replyQueue);
				Message replyMessage = consumer.receive(5000);  // Timeout di 5 secondi

				if (replyMessage != null) {
					// Elabora la risposta
					if (replyMessage instanceof TextMessage) {
						TextMessage textReply = (TextMessage) replyMessage;
						logger.info("Received response: {}", textReply.getText());
					} else {
						logger.error("Unexpected message type in response");
					}
				} else {
					logger.warn("No response received within timeout period");
				}
			} else {
				logger.info("No response queue provided, not waiting for response.");
			}

		} catch (Exception e) {
			throw formatWorkflowManagerException("Error sending message to queue: " + requestQueueName, e);
		}

		return sendBean;
	}

}
