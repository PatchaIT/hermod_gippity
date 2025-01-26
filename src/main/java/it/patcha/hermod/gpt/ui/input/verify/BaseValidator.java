package it.patcha.hermod.gpt.ui.input.verify;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;

/** Abstract containing general use methods for TaskExecutor. */
public abstract class BaseValidator extends HermodClass implements Validator {

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new ValidatorException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(String message, String code) {
		return formatValidatorException(message, code, null);
	}

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(String message, Throwable cause) {
		return formatValidatorException(message, null, cause);
	}

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(String message) {
		return formatValidatorException(message, null, null);
	}

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(ErrorType errorType, Throwable cause) {
		return formatValidatorException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a ValidatorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException formatValidatorException(ErrorType errorType) {
		return formatValidatorException(errorType.getMessage(), errorType.getCode(), null);
	}

	/**
	 * Returns a ValidatorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException includeAndFormatIntoValidatorException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, ValidatorException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new ValidatorException(e);
		}
	}

	/**
	 * Returns a ValidatorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected ValidatorException includeAndFormatIntoValidatorException(Exception exception) {
		return includeAndFormatIntoValidatorException(exception, null);
	}

}
