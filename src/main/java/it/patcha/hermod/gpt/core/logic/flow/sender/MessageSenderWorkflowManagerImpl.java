package it.patcha.hermod.gpt.core.logic.flow.sender;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.core.logic.flow.BaseWorkflowManager;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;
import it.patcha.hermod.gpt.core.logic.task.sender.MessageSenderTaskExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Implementation of MessageSenderWorkflowManager in charge of sending message. */
@Component
@RequiredArgsConstructor
public class MessageSenderWorkflowManagerImpl extends BaseWorkflowManager implements MessageSenderWorkflowManager {

	private final MessageSenderTaskExecutor messageSenderTaskExecutor;

	@Override
	public SendBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException {
		SendBean sendBean = null;

		try {
			sendBean = messageSenderTaskExecutor.castToSendBean(super.handleWorkflow(workflowInput));
			sendBean = messageSenderTaskExecutor.readFile(sendBean);
			sendBean = messageSenderTaskExecutor.sendMessage(sendBean);

		} catch (TaskExecutorException e) {
			throw new WorkflowManagerException(e);
		}

		return sendBean;
	}

}
