package it.patcha.hermod.gpt.common.bean.core.logic;

import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import jakarta.jms.ConnectionFactory;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.io.Serial;

/** This bean contains necessary data to send a message. */
@Getter
@Setter
public class SendBean extends HermodBean {

	@Serial
	private static final long serialVersionUID = 1318371049411971948L;
	private static final int ID_ODD_NUM = 1;

	private String messageText;
	private File messageFile;
	private String requestQueueName;
	private String replyQueueName;
	private transient ConnectionFactory connectionFactory;

	public SendBean() {
	}

	public SendBean(String messageText, File messageFile, String requestQueueName, ConnectionFactory connectionFactory) {
		this.messageText = messageText;
		this.messageFile = messageFile;
		this.requestQueueName = requestQueueName;
		this.connectionFactory = connectionFactory;
	}

	public SendBean(String messageText, String messageFilePath, String requestQueueName, ConnectionFactory connectionFactory) {
		this(messageText, new File(messageFilePath), requestQueueName, connectionFactory);
	}

	public SendBean(String messageText, String requestQueueName, ConnectionFactory connectionFactory) {
		this.messageText = messageText;
		this.requestQueueName = requestQueueName;
		this.connectionFactory = connectionFactory;
	}

	public SendBean(File messageFile, String requestQueueName, ConnectionFactory connectionFactory) {
		this.messageFile = messageFile;
		this.requestQueueName = requestQueueName;
		this.connectionFactory = connectionFactory;
	}

	public void setMessageFilePath(String messageFile) {
		this.messageFile = new File(messageFile);
	}

	@Override
	public boolean equals (Object obj) {
		if(!(obj instanceof SendBean that))
			return false;

		if (that == this)
			return true;

		return new EqualsBuilder()
				.append(this.messageText, that.getMessageText())
				.append(this.messageFile, that.getMessageFile())
				.append(this.requestQueueName, that.getRequestQueueName())
				.append(this.replyQueueName, that.getReplyQueueName())
				.append(this.connectionFactory, that.getConnectionFactory())

				.append(this.isSuccessful(), that.isSuccessful())

				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(
				HermodConstants.getInitialNonZeroOddNumbers()[ID_ODD_NUM],
				HermodConstants.getMultiplierNonZeroOddNumbers()[ID_ODD_NUM]
		)
				.append(this.messageText)
				.append(this.messageFile)
				.append(this.requestQueueName)
				.append(this.replyQueueName)
				.append(this.connectionFactory)

				.append(this.isSuccessful())

				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("messageText", this.messageText)
				.append("messageFile", this.messageFile)
				.append("requestQueueName", this.requestQueueName)
				.append("replyQueueName", this.replyQueueName)
				.append("connectionFactory", this.connectionFactory)

				.append("successful", this.isSuccessful())

				.toString();
	}

}
