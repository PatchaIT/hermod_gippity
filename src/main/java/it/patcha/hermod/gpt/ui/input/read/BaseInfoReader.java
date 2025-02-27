package it.patcha.hermod.gpt.ui.input.read;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR01;

/** Abstract containing base checks on methods bean parameter and general use methods for InfoReader. */
public abstract class BaseInfoReader extends HermodClass implements InfoReader {

	@Override
	public HermodBean handleOptions(HermodBean readerInput) throws InfoReaderException {
		logger.info("{} received readerInput: {}", this.getClass().getSimpleName(), readerInput);

		if (readerInput == null)
			throw formatInfoReaderException(IR01);

		readerInput.setSuccessful(false);

		return readerInput;
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new InfoReaderException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(String message, String code) {
		return formatInfoReaderException(message, code, null);
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(String message, Throwable cause) {
		return formatInfoReaderException(message, null, cause);
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(String message) {
		return formatInfoReaderException(message, null, null);
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(ErrorType errorType, Throwable cause) {
		if (errorType == null)
			return formatInfoReaderException(null, null, cause);
		return formatInfoReaderException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a InfoReaderException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException formatInfoReaderException(ErrorType errorType) {
		return formatInfoReaderException(errorType.getMessage(), errorType.getCode(), null);
	}

	/**
	 * Returns a InfoReaderException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException includeAndFormatIntoInfoReaderException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, InfoReaderException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new InfoReaderException(e);
		}
	}

	/**
	 * Returns a InfoReaderException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected InfoReaderException includeAndFormatIntoInfoReaderException(Exception exception) {
		return includeAndFormatIntoInfoReaderException(exception, null);
	}

}
