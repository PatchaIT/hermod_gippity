package it.patcha.hermod.gpt.core.logic.task;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.core.logic.task.common.error.TaskExecutorException;

/** Abstract containing general use methods for TaskExecutor. */
public abstract class BaseTaskExecutor extends HermodClass implements TaskExecutor {

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new TaskExecutorException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(String message, String code) {
		return formatTaskExecutorException(message, code, null);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(String message, Throwable cause) {
		return formatTaskExecutorException(message, null, cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(String message) {
		return formatTaskExecutorException(message, null, null);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(ErrorType errorType, Throwable cause) {
		if (errorType == null)
			return formatTaskExecutorException(null, null, cause);
		return formatTaskExecutorException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a TaskExecutorException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException formatTaskExecutorException(ErrorType errorType) {
		return formatTaskExecutorException(errorType.getMessage(), errorType.getCode(), null);
	}

	/**
	 * Returns a TaskExecutorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException includeAndFormatIntoTaskExecutorException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, TaskExecutorException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new TaskExecutorException(e);
		}
	}

	/**
	 * Returns a TaskExecutorException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected TaskExecutorException includeAndFormatIntoTaskExecutorException(Exception exception) {
		return includeAndFormatIntoTaskExecutorException(exception, null);
	}

}
