package it.patcha.hermod.gpt.ui.input.dispatch;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.JD01;

/** Abstract containing base checks on methods bean parameter and general use methods for JobDispatcher. */
public abstract class BaseJobDispatcher extends HermodClass implements JobDispatcher {

	@Override
	public HermodBean handleJobs(HermodBean dispatcherInput) throws JobDispatcherException {
		logger.debug("{} received dispatcherInput: {}", this.getClass().getSimpleName(), dispatcherInput);

		if (dispatcherInput == null)
			throw new JobDispatcherException(JD01, this.getClass());

		dispatcherInput.setSuccessful(false);

		return dispatcherInput;
	}

}
