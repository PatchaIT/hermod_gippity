package it.patcha.hermod.gpt.core.logic.flow;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.WM01;

/** Abstract containing base checks on methods bean parameter and general use methods for WorkflowManager. */
public abstract class BaseWorkflowManager extends HermodClass implements WorkflowManager {

	@Override
	public HermodBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException {
		logger.debug("{} received workflowInput: {}", this.getClass().getSimpleName(), workflowInput);

		if (workflowInput == null)
			throw new WorkflowManagerException(WM01, this.getClass());

		workflowInput.setSuccessful(false);

		return workflowInput;
	}

}
