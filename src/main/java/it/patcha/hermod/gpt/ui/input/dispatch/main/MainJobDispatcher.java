package it.patcha.hermod.gpt.ui.input.dispatch.main;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.ui.input.dispatch.JobDispatcher;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;

/** Interface for MainJobDispatcher in charge of delegate from the main thread. */
public interface MainJobDispatcher extends JobDispatcher {

	/**
	 * Manages main thread delegating controls and logics to other classes.
	 * The parameter is expected to be an {@link ArgsBean}.
	 *
	 * @param dispatcherInput {@link ArgsBean} contains all necessary data for this handler
	 * @return a {@link SendBean} with {@link SendBean#isSuccessful()} set to {@code true}
	 *   {@literal (ndR: this is still under decision)}
	 * @throws JobDispatcherException if something goes wrong
	 */
	@Override
	HermodBean handleJobs(HermodBean dispatcherInput) throws JobDispatcherException;

}
