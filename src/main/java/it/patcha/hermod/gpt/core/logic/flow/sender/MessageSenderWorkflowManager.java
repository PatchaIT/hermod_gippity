package it.patcha.hermod.gpt.core.logic.flow.sender;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.core.logic.flow.WorkflowManager;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;

/** Interface for MessageSenderWorkflowManager in charge of sending message. */
public interface MessageSenderWorkflowManager extends WorkflowManager {

	/**
	 * Sends a JMS message using info contained into the parameter.
	 * The parameter is expected to be a {@link SendBean}.
	 *
	 * @param workflowInput {@link SendBean} contains all necessary data for this handler
	 * @return the {@link SendBean} with {@link SendBean#isSuccessful()} set to {@code true}
	 * @throws WorkflowManagerException if something goes wrong
	 */
	@Override
	SendBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException;

}
