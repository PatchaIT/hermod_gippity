package it.patcha.hermod.gpt.ui.input.delegate.main;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;
import it.patcha.hermod.gpt.core.logic.flow.sender.MessageSenderWorkflowManager;
import it.patcha.hermod.gpt.ui.input.delegate.BaseDelegator;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;
import jakarta.jms.ConnectionFactory;
import org.springframework.stereotype.Component;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.JD02;

/** Implementation of MessageSenderTaskExecutor in charge of delegating for {@link MainJobDispatcher}. */
@Component
public class MainDelegatorImpl extends BaseDelegator implements MainDelegator {

	@Override
	public ArgsBean castToArgsBean(HermodBean hermodBean) throws DelegatorException {
		if (hermodBean instanceof ArgsBean)
			return (ArgsBean) hermodBean;

		else
			throw formatDelegatorException(
					String.format(
							JD02.getMessage(), getSimpleName(ArgsBean.class), getSimpleName(hermodBean)),
					JD02.getCode());
	}

	@Override
	public ArgsBean delegateArgs(ArgInfoReader argInfoReader, ArgsBean argsBean) throws DelegatorException {
		try {
			return argInfoReader.handleOptions(argsBean);

		} catch (InfoReaderException e) {
			throw new DelegatorException(e);
		}
	}

	@Override
	public SendBean delegateMessage(MessageSenderWorkflowManager messageSender, ArgsBean argsBean) throws DelegatorException {
		ConnectionFactory connectionFactory = HermodUtils.getConnectionFactory(argsBean.getConnectionFactoryUrl());

		SendBean sendBean = new SendBean(
				argsBean.getMessageText(),
				argsBean.getMessageFile(),
				argsBean.getRequestQueueName(),
				connectionFactory);

		try {
			sendBean = messageSender.handleWorkflow(sendBean);

		} catch (WorkflowManagerException e) {
			throw new DelegatorException(e);
		}

		return sendBean;
	}

}
