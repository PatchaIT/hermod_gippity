package it.patcha.hermod.gpt.ui.input.verify.args;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import it.patcha.hermod.gpt.ui.input.verify.Validator;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;

/** Interface for MessageSenderTaskExecutor in charge of validating for {@link ArgInfoReader}. */
public interface ArgValidator extends Validator {

	/** Check if parameter is the expected type and casts it.
	 *
	 * @param hermodBean the object for which to check the type
	 * @return the cast object
	 * @throws ValidatorException if anything goes wrong
	 */
	ArgsBean castToArgsBean(HermodBean hermodBean) throws ValidatorException;

	/**
	 * Validate command line arguments.
	 *
	 * @param argsBean the object containing arguments values
	 * @return the populated object
	 * @throws ValidatorException if anything goes wrong
	 */
	ArgsBean validateArgs(ArgsBean argsBean) throws ValidatorException;

}
