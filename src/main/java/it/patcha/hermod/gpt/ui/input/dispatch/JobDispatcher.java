package it.patcha.hermod.gpt.ui.input.dispatch;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;

/** Interface for JobDispatcher, forcing them to have an handleWorkflow method and providing its general javadoc. */
public interface JobDispatcher {

	/**
	 * Main handler of a JobDispatcher, to delegate controls and logics to dedicated classes.
	 * Controls will go to InfoReaders and logics go to WorkflowManagers.
	 *
	 * @param dispatcherInput the bean containing all necessary data for this handler
	 * @return the resulting HermodBean after processing the jobs
	 * @throws JobDispatcherException if anything goes wrong
	 */
	HermodBean handleJobs(HermodBean dispatcherInput) throws JobDispatcherException;

}
