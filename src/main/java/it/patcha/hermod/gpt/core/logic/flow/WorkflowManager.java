package it.patcha.hermod.gpt.core.logic.flow;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;

/** Interface for WorkflowManager, forcing them to have an handleWorkflow method and providing its general javadoc. */
public interface WorkflowManager {

	/**
	 * Main handler of a WorkflowManager, to execute logic steps.
	 *
	 * @param workflowInput bean contains all necessary data for this handler
	 * @return the resulting HermodBean after processing the logics
	 * @throws WorkflowManagerException if anything goes wrong
	 */
	HermodBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException;

}
