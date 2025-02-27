package it.patcha.hermod.gpt.ui.input.verify.args;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import it.patcha.hermod.gpt.ui.input.verify.BaseValidator;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;
import org.springframework.stereotype.Component;

import static it.patcha.hermod.gpt.common.constant.HermodConstants.Args.fromString;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR02;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR03;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR04;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR99;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI01;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI02;

/** Implementation of MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
@Component
public class ArgValidatorImpl extends BaseValidator implements ArgValidator {

	@Override
	public ArgsBean castToArgsBean(HermodBean hermodBean) throws ValidatorException {
		if (hermodBean instanceof ArgsBean argsBean)
			return argsBean;

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
		boolean gui = false;

		int i = 0;
		boolean isValued;
		while (i < args.length) {
			String arg = args[i];
			String value = (i + 1 < args.length) ? args[i + 1] : null;

			try {
				isValued = switch (fromString(arg)) {
					case GUI -> {
						gui = true;
						yield true;
					}
					case CONNECTION_FACTORY_URL -> {
						connectionFactoryUrl = value;
						yield false;
					}
					case REQUEST_QUEUE_NAME -> {
						requestQueueName = value;
						yield false;
					}
					case JMS_MESSAGE_TEXT -> {
						messageText = value;
						yield false;
					}
					case JMS_MESSAGE_FILE_PATH -> {
						messageFilePath = value;
						yield false;
					}
					default -> throw includeAndFormatIntoValidatorException(
							new IllegalArgumentException(
									String.format(IR03.getMessage(), args[i])), IR03.getCode());
				};

			} catch (IllegalArgumentException e) {
				throw includeAndFormatIntoValidatorException(
						new IllegalArgumentException(
								String.format(e.getMessage(), args[i])), IR99.getCode());
			}

			if (isValued) {
				i++;
			} else {
				if (value != null) {
					i += 2;

				} else {
					throw includeAndFormatIntoValidatorException(
							new IllegalArgumentException(
									String.format(IR04.getMessage(), args[i])), IR04.getCode());
				}
			}

		}

		if (gui)
			throw formatValidatorException(UI02);

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
