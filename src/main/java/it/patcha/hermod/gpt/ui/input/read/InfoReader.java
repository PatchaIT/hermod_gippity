package it.patcha.hermod.gpt.ui.input.read;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;

/** Interface for InfoReader, forcing them to have an handleWorkflow method and providing its general javadoc. */
public interface InfoReader {

	/**
	 * Main handler of an InfoReader, to validate information before feeding logics.
	 *
	 * @param readerInput the bean containing all necessary data for this handler
	 * @return the resulting HermodBean after processing the information
	 * @throws InfoReaderException if anything goes wrong
	 */
	HermodBean handleOptions(HermodBean readerInput) throws InfoReaderException;

}
