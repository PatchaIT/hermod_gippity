package it.patcha.hermod.gpt.ui.input.dispatch;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.JD01;

/** Abstract containing base checks on methods bean parameter and general use methods for JobDispatcher. */
public abstract class BaseJobDispatcher extends HermodClass implements JobDispatcher {

	@Override
	public HermodBean handleJobs(HermodBean dispatcherInput) throws JobDispatcherException {
		logger.info("{} received dispatcherInput: {}", this.getClass().getSimpleName(), dispatcherInput);

		if (dispatcherInput == null)
			throw formatJobDispatcherException(JD01);

		dispatcherInput.setSuccessful(false);

		return dispatcherInput;
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new JobDispatcherException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(String message, String code) {
		return formatJobDispatcherException(message, code, null);
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(String message, Throwable cause) {
		return formatJobDispatcherException(message, null, cause);
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(String message) {
		return formatJobDispatcherException(message, null, null);
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(ErrorType errorType, Throwable cause) {
		if (errorType == null)
			return formatJobDispatcherException(null, null, cause);
		return formatJobDispatcherException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a JobDispatcherException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException formatJobDispatcherException(ErrorType errorType) {
		return formatJobDispatcherException(errorType.getMessage(), errorType.getCode(), null);
	}

	/**
	 * Returns a JobDispatcherException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException includeAndFormatIntoJobDispatcherException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, JobDispatcherException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new JobDispatcherException(e);
		}
	}

	/**
	 * Returns a JobDispatcherException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected JobDispatcherException includeAndFormatIntoJobDispatcherException(Exception exception) {
		return includeAndFormatIntoJobDispatcherException(exception, null);
	}

}
