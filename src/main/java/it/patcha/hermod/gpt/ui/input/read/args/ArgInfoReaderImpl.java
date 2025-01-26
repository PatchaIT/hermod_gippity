package it.patcha.hermod.gpt.ui.input.read.args;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.ui.input.read.BaseInfoReader;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;
import it.patcha.hermod.gpt.ui.input.verify.args.ArgValidator;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Implementation of ArgInfoReader in charge of reading and validating command line arguments. */
@Component
public class ArgInfoReaderImpl extends BaseInfoReader implements ArgInfoReader {

	@Autowired
	private ArgValidator argValidator;

	@Override
	public ArgsBean handleOptions(HermodBean readerInput) throws InfoReaderException {
		ArgsBean argsBean = null;

		try {
			argsBean = argValidator.castToArgsBean(super.handleOptions(readerInput));
			argsBean = argValidator.validateArgs(argsBean);

		} catch (ValidatorException e) {
			throw new InfoReaderException(e);
		}

		return argsBean;
	}

}
