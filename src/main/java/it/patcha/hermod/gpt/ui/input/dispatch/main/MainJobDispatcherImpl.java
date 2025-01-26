package it.patcha.hermod.gpt.ui.input.dispatch.main;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.core.logic.flow.sender.MessageSenderWorkflowManager;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import it.patcha.hermod.gpt.ui.input.delegate.main.MainDelegator;
import it.patcha.hermod.gpt.ui.input.dispatch.BaseJobDispatcher;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Implementation of MainJobDispatcher in charge of delegate from the main thread. */
@Component
public class MainJobDispatcherImpl extends BaseJobDispatcher implements MainJobDispatcher {

	@Autowired
	private MainDelegator mainDelegator;
	@Autowired
	private ArgInfoReader argInfoReader;
	@Autowired
	private MessageSenderWorkflowManager messageSender;

	@Override
	public HermodBean handleJobs(HermodBean dispatcherInput) throws JobDispatcherException {
		ArgsBean argsBean = null;
		SendBean sendBean = null;

		try {
			argsBean = mainDelegator.castToArgsBean(super.handleJobs(dispatcherInput));
			argsBean = mainDelegator.delegateArgs(argInfoReader, argsBean);
			sendBean = mainDelegator.delegateMessage(messageSender, argsBean);

		} catch (DelegatorException e) {
			throw new JobDispatcherException(e);
		}

		return sendBean; // TODO: It's still not said this will be the final returned bean
	}

}
