package it.patcha.hermod.gpt.core.logic.task.sender;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.core.logic.task.TaskExecutor;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;

/** Interface for MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
public interface MessageSenderTaskExecutor extends TaskExecutor {

	/** Check if parameter is the expected type and casts it.
	 *
	 * @param hermodBean the object for which to check the type
	 * @return the cast object
	 * @throws TaskExecutorException if anything goes wrong
	 */
	SendBean castToSendBean(HermodBean hermodBean) throws TaskExecutorException;

	/**
	 * Check if message is supposed to be stored in a file
	 *   and reads the file, if necessary.
	 * <p>
	 * If both {@link SendBean#getMessageText()} and {@link SendBean#getMessageFile()}
	 *   are populated into {@link SendBean}, and file on {@link SendBean#getMessageFile()}
	 *   is found, the content of the file will overwrite the {@link SendBean#getMessageText()}.
	 * </p>
	 * Otherwise, if there's any issue on reading {@link SendBean#getMessageFile()},
	 *   it will cast {@link TaskExecutorException} unless {@link SendBean#getMessageText()}
	 *   is populated and so it will be used as backup.
	 * <p>
	 * So if both {@link SendBean#getMessageText()} and {@link SendBean#getMessageFile()}
	 *   are populated, {@link SendBean#getMessageText()} will be used as backup only
	 *   in case there's any issue on reading {@link SendBean#getMessageFile()} file.
	 * </p>
	 *
	 * @param sendBean the object containing arguments values
	 * @return the populated object
	 * @throws TaskExecutorException if anything goes wrong
	 */
	SendBean readFile(SendBean sendBean) throws TaskExecutorException;

	/**
	 * Sends the message to JMS requestQueue.
	 *
	 * @param sendBean the object containing arguments values
	 * @return the populated object
	 * @throws TaskExecutorException if anything goes wrong
	 */
	SendBean sendMessage(SendBean sendBean) throws TaskExecutorException;

}
