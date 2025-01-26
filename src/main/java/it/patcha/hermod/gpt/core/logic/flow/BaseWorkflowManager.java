package it.patcha.hermod.gpt.core.logic.flow;

import it.patcha.hermod.gpt.common.HermodClass;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.WM01;

/** Abstract containing base checks on methods bean parameter and general use methods for WorkflowManager. */
public abstract class BaseWorkflowManager extends HermodClass implements WorkflowManager {

	@Override
	public HermodBean handleWorkflow(HermodBean workflowInput) throws WorkflowManagerException {
		logger.info("{} received workflowInput: {}", this.getClass().getSimpleName(), workflowInput);

		if (workflowInput == null)
			throw formatWorkflowManagerException(WM01);

		workflowInput.setSuccessful(false);

		return workflowInput;
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(String message, String code, Throwable cause) {
		return HermodUtils.formatHermodException(new WorkflowManagerException(), this.getClass(), message, code, cause);
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(String message, String code) {
		return formatWorkflowManagerException(message, code, null);
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(String message, Throwable cause) {
		return formatWorkflowManagerException(message, null, cause);
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param message the exception message to format
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(String message) {
		return formatWorkflowManagerException(message, null, null);
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @param cause the exception for which this exception is being generated, if any
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(ErrorType errorType, Throwable cause) {
		if (errorType == null)
			return formatWorkflowManagerException(null, null, cause);
		return formatWorkflowManagerException(errorType.getMessage(), errorType.getCode(), cause);
	}

	/**
	 * Returns a WorkflowManagerException with message formatted by project's standard.
	 *
	 * @param errorType the reference errorType containing the exception message and code to format
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException formatWorkflowManagerException(ErrorType errorType) {
		return formatWorkflowManagerException(errorType, null);
	}

	/**
	 * Returns a WorkflowManagerException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @param code the exception code
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException includeAndFormatIntoWorkflowManagerException(Exception exception, String code) {
		try {
			return HermodUtils.formatIntoHermodException(
					exception, WorkflowManagerException.class, this.getClass(), code);
		} catch (HermodException e) {
			return new WorkflowManagerException(e);
		}
	}

	/**
	 * Returns a WorkflowManagerException with all characteristics of given exception,
	 * but the message formatted by project's standard, and the cause will be the given exception.
	 *
	 * @param exception the cause exception from which message will be taken and formatted
	 * @return the wanted exception with formatted message
	 */
	protected WorkflowManagerException includeAndFormatIntoWorkflowManagerException(Exception exception) {
		return includeAndFormatIntoWorkflowManagerException(exception, null);
	}

}
