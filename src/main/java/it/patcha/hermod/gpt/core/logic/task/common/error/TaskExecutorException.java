package it.patcha.hermod.gpt.core.logic.task.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for Delegator error management. */
public class TaskExecutorException extends HermodException {

	@Serial
	private static final long serialVersionUID = -3859484707662217446L;

	public TaskExecutorException() {
	}

	public <T extends HermodException> TaskExecutorException(T child) {
		super(child);
	}

	public TaskExecutorException(String message) {
		super(message);
	}

	public TaskExecutorException(Class<?> source) {
		super(source);
	}

	public TaskExecutorException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskExecutorException(String message, String code) {
		super(message, code);
	}

	public TaskExecutorException(String message, Class<?> source) {
		super(message, source);
	}

	public TaskExecutorException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public TaskExecutorException(String message, String code, Class<?> source) {
		super(message, code, source);
	}

	public TaskExecutorException(String message, String code, Class<?> source, Throwable cause) {
		super(message, code, source, cause);
	}

	public TaskExecutorException(ErrorType errorType) {
		super(errorType);
	}

	public TaskExecutorException(ErrorType errorType, Throwable cause) {
		super(errorType, cause);
	}

	public TaskExecutorException(ErrorType errorType, Class<?> source) {
		super(errorType, source);
	}

	public TaskExecutorException(ErrorType errorType, Class<?> source, Throwable cause) {
		super(errorType, source, cause);
	}

	public TaskExecutorException(Throwable cause) {
		super(cause);
	}

}
