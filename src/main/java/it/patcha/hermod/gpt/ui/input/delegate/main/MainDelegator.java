package it.patcha.hermod.gpt.ui.input.delegate.main;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.core.logic.flow.sender.MessageSenderWorkflowManager;
import it.patcha.hermod.gpt.ui.input.delegate.Delegator;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;

/** Interface for MessageSenderTaskExecutor in charge of delegating for {@link MainJobDispatcher}. */
public interface MainDelegator extends Delegator {

	/** Check if parameter is the expected type and casts it.
	 *
	 * @param hermodBean the object for which to check the type
	 * @return the cast object
	 * @throws DelegatorException if anything goes wrong
	 */
	ArgsBean castToArgsBean(HermodBean hermodBean) throws DelegatorException;

	/**
	 * Delegates the management of arguments to dedicated InfoReader.
	 *
	 * @param argInfoReader the InfoReader dedicated to arguments management
	 * @param argsBean the object containing arguments values
	 * @return the populated object
	 * @throws DelegatorException if anything goes wrong
	 */
	ArgsBean delegateArgs(ArgInfoReader argInfoReader, ArgsBean argsBean) throws DelegatorException;

	/** Delegates the logics of sending message to dedicated WorkflowManager.
	 *
	 * @param messageSender the WorkflowManager dedicated to sending message
	 * @param argsBean the object containing necessary info
	 * @return the updated object
	 * @throws DelegatorException if anything goes wrong
	 */
	SendBean delegateMessage(MessageSenderWorkflowManager messageSender, ArgsBean argsBean) throws DelegatorException;

}
