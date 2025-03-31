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
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI03;

/** Implementation of MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
@Component
public class ArgValidatorImpl extends BaseValidator implements ArgValidator {

	private record ParsedMessage(String messageText, int argsRead) {}

	@Override
	public ArgsBean castToArgsBean(HermodBean hermodBean) throws ValidatorException {
		if (hermodBean instanceof ArgsBean argsBean)
			return argsBean;

		else
			throw new ValidatorException(
					String.format(
							IR02.getMessage(), getSimpleName(ArgsBean.class), getSimpleName(hermodBean)),
					IR02.getCode(), this.getClass());
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
		int argsRead;
		while (i < args.length) {
			String arg = args[i];
			String value = (i + 1 < args.length) ? args[i + 1] : null;

			try {
				argsRead = switch (fromString(arg)) {
					case GUI -> {
						gui = true;
						yield 1;
					}
					case CONNECTION_FACTORY_URL -> {
						connectionFactoryUrl = value;
						yield 2;
					}
					case REQUEST_QUEUE_NAME -> {
						requestQueueName = value;
						yield 2;
					}
					case JMS_MESSAGE_TEXT -> {
						ParsedMessage parsed = textMessageParser(args, i, value);
						messageText = parsed.messageText();
						yield parsed.argsRead();
					}
					case JMS_MESSAGE_FILE_PATH -> {
						messageFilePath = value;
						yield 2;
					}
					default -> {
						String message = String.format(IR03.getMessage(), args[i]);
						throw new ValidatorException(message, IR03.getCode(), this.getClass(),
								new IllegalArgumentException(message));
					}
				};

			} catch (IllegalArgumentException e) {
				String message = String.format(e.getMessage(), args[i]);
				throw new ValidatorException(message, IR99.getCode(), this.getClass(),
						new IllegalArgumentException(message));
			}

			if (argsRead > 1 && value == null) {
				String message = String.format(IR04.getMessage(), args[i]);
				throw new ValidatorException(message, IR04.getCode(), this.getClass(),
						new IllegalArgumentException(message));
			}

			i += argsRead;
		}

		if (gui)
			throw new ValidatorException(UI02, this.getClass());

		if (connectionFactoryUrl == null || requestQueueName == null || (messageText == null && messageFilePath == null)) {
			logger.error(UI03.getMessage(), connectionFactoryUrl, requestQueueName, messageText, messageFilePath);
			throw new ValidatorException(UI01, this.getClass());
		}

		argsBean.setConnectionFactoryUrl(connectionFactoryUrl);
		argsBean.setRequestQueueName(requestQueueName);
		argsBean.setMessageText(messageText);
		argsBean.setMessageFilePath(messageFilePath);
		argsBean.setSuccessful(true);

		return argsBean;
	}

	private ParsedMessage textMessageParser(String[] args, int i, String value) {
		String messageText;
		int argsRead;

		if (value == null) {
			messageText = null;
			argsRead = 2;

		} else {
			StringBuilder textBuilder = new StringBuilder();
			int j = i + 1;

			while (j < args.length && !args[j].startsWith("-")) {
				textBuilder.append(args[j]).append(" ");
				j++;
			}

			messageText = textBuilder.toString().trim();
			argsRead = j - i;
		}

		return new ParsedMessage(messageText, argsRead);
	}

}
