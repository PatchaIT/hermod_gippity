package it.patcha.hermod.gpt.ui.input.verify.args;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import it.patcha.hermod.gpt.ui.input.verify.BaseValidator;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;
import org.springframework.stereotype.Component;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR02;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR03;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR04;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI01;

/** Implementation of MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
@Component
public class ArgValidatorImpl extends BaseValidator implements ArgValidator {

	@Override
	public ArgsBean castToArgsBean(HermodBean hermodBean) throws ValidatorException {
		if (hermodBean instanceof ArgsBean)
			return (ArgsBean) hermodBean;

		else
			throw formatValidatorException(
					String.format(
							IR02.getMessage(), getSimpleName(ArgsBean.class), getSimpleName(hermodBean)),
					IR02.getCode());
	}

	@Override
	public ArgsBean validateArgs(ArgsBean argsBean) throws ValidatorException {
		String[] args = argsBean.getArgs();
		String connectionFactoryUrl = null;
		String requestQueueName = null;
		String messageText = null;
		String messageFilePath = null;

		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case HermodConstants.ARG_CONNECTION_FACTORY_URL:
						connectionFactoryUrl = args[++i];
						break;
					case HermodConstants.ARG_REQUEST_QUEUE_NAME:
						requestQueueName = args[++i];
						break;
					case HermodConstants.ARG_JMS_MESSAGE_TEXT:
						messageText = args[++i];
						break;
					case HermodConstants.ARG_JMS_MESSAGE_FILE_PATH:
						messageFilePath = args[++i];
						break;
					default:
						throw includeAndFormatIntoValidatorException(
								new IllegalArgumentException(
										String.format(IR03.getMessage(), args[i])), IR03.getCode());
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			throw includeAndFormatIntoValidatorException(
					new IllegalArgumentException(
							String.format(IR04.getMessage(), args[args.length - 1]), e), IR04.getCode());
		}

		if (connectionFactoryUrl == null || requestQueueName == null || messageText == null)
			throw formatValidatorException(UI01);

		argsBean.setConnectionFactoryUrl(connectionFactoryUrl);
		argsBean.setRequestQueueName(requestQueueName);
		argsBean.setMessageText(messageText);
		argsBean.setMessageFilePath(messageFilePath);
		argsBean.setSuccessful(true);

		return argsBean;
	}

}
