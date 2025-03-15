package it.patcha.hermod.gpt.ui.input.read;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR01;

/** Abstract containing base checks on methods bean parameter and general use methods for InfoReader. */
public abstract class BaseInfoReader extends HermodClass implements InfoReader {

	@Override
	public HermodBean handleOptions(HermodBean readerInput) throws InfoReaderException {
		logger.debug("{} received readerInput: {}", this.getClass().getSimpleName(), readerInput);

		if (readerInput == null)
			throw new InfoReaderException(IR01, this.getClass());

		readerInput.setSuccessful(false);

		return readerInput;
	}

}
