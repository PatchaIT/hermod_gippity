package it.patcha.hermod.gpt.ui.input.read.args;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.ui.input.read.InfoReader;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;

/** Interface for ArgInfoReader in charge of reading and validating command line arguments. */
public interface ArgInfoReader extends InfoReader {

	/**
	 * Reads and validate arguments from command line.
	 * The parameter is expected to be an {@link ArgsBean}.
	 *
	 * @param readerInput {@link ArgsBean} contains all necessary data for this handler
	 * @return the {@link ArgsBean} with {@link ArgsBean#isSuccessful()} set to {@code true}
	 * @throws InfoReaderException if something goes wrong
	 */
	@Override
	ArgsBean handleOptions(HermodBean readerInput) throws InfoReaderException;

}
