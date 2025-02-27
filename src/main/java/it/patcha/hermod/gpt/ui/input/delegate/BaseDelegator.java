package it.patcha.hermod.gpt.ui.input.delegate;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;

/** Abstract containing general use methods for Delegator. */
public abstract class BaseDelegator extends HermodClass implements Delegator {

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new DelegatorException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(String message, String code) {
		return formatDelegatorException(message, code, null);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(String message, Throwable cause) {
		return formatDelegatorException(message, null, cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(String message) {
		return formatDelegatorException(message, null, null);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(ErrorType errorType, Throwable cause) {
		if (errorType == null)
			return formatDelegatorException(null, null, cause);
		return formatDelegatorException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException formatDelegatorException(ErrorType errorType) {
		return formatDelegatorException(errorType.getMessage(), errorType.getCode(), null);
	}

	/**
	 * Returns a TaskExecutorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException includeAndFormatIntoDelegatorException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, DelegatorException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new DelegatorException(e);
		}
	}

	/**
	 * Returns a TaskExecutorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected DelegatorException includeAndFormatIntoDelegatorException(Exception exception) {
		return includeAndFormatIntoDelegatorException(exception, null);
	}

}
