package it.patcha.hermod.gpt.common.bean.ui.input;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.io.Serial;

/**
 * This bean contains command line arguments
 *   and all data expected to be got from arguments.
 */
public class ArgsBean extends HermodBean {

	@Serial
	private static final long serialVersionUID = 8414209493919750067L;
	private static final int iOddNums = 2;

	private String[] args;

	private String messageText;
	private File messageFile;
	private String requestQueueName;
	private String replyQueueName;
	private String connectionFactoryUrl;

	public ArgsBean() {
	}

	public ArgsBean(String[] args) {
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public File getMessageFile() {
		return messageFile;
	}

	public void setMessageFile(File messageFile) {
		this.messageFile = messageFile;
	}

	public void setMessageFilePath(String messageFilePath) {
		this.messageFile = new File(messageFilePath);
	}

	public String getRequestQueueName() {
		return requestQueueName;
	}

	public void setRequestQueueName(String requestQueueName) {
		this.requestQueueName = requestQueueName;
	}

	public String getReplyQueueName() {
		return replyQueueName;
	}

	public void setReplyQueueName(String replyQueueName) {
		this.replyQueueName = replyQueueName;
	}

	public String getConnectionFactoryUrl() {
		return connectionFactoryUrl;
	}

	public void setConnectionFactoryUrl(String connectionFactoryUrl) {
		this.connectionFactoryUrl = connectionFactoryUrl;
	}

	@Override
	public boolean equals (Object obj) {
		if(!(obj instanceof ArgsBean that))
			return false;

		if (that == this)
			return true;

		return new EqualsBuilder()
				.append(this.args, that.getArgs())
				.append(this.messageText, that.getMessageText())
				.append(this.messageFile, that.getMessageFile())
				.append(this.requestQueueName, that.getRequestQueueName())
				.append(this.replyQueueName, that.getReplyQueueName())
				.append(this.connectionFactoryUrl, that.getConnectionFactoryUrl())

				.append(this.isSuccessful(), that.isSuccessful())

				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(
				HermodConstants.initialNonZeroOddNumbers[iOddNums],
				HermodConstants.multiplierNonZeroOddNumbers[iOddNums]
		)
				.append(this.args)
				.append(this.messageText)
				.append(this.messageFile)
				.append(this.requestQueueName)
				.append(this.replyQueueName)
				.append(this.connectionFactoryUrl)

				.append(this.isSuccessful())

				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("args", this.args)
				.append("messageText", this.messageText)
				.append("messageFile", this.messageFile)
				.append("requestQueueName", this.requestQueueName)
				.append("replyQueueName", this.replyQueueName)
				.append("connectionFactoryUrl", this.connectionFactoryUrl)

				.append("successful", this.isSuccessful())

				.toString();
	}

}
