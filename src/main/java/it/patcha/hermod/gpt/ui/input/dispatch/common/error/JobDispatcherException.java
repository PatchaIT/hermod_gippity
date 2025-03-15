package it.patcha.hermod.gpt.ui.input.dispatch.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for JobDispatcher error management. */
public class JobDispatcherException extends HermodException {

	@Serial
	private static final long serialVersionUID = 5598893335348369295L;

	public JobDispatcherException() {
	}

	public <T extends HermodException> JobDispatcherException(T child) {
		super(child);
	}

	public JobDispatcherException(String message) {
		super(message);
	}

	public JobDispatcherException(Class<?> source) {
		super(source);
	}

	public JobDispatcherException(String message, Throwable cause) {
		super(message, cause);
	}

	public JobDispatcherException(String message, String code) {
		super(message, code);
	}

	public JobDispatcherException(String message, Class<?> source) {
		super(message, source);
	}

	public JobDispatcherException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public JobDispatcherException(String message, String code, Class<?> source) {
		super(message, code, source);
	}

	public JobDispatcherException(String message, String code, Class<?> source, Throwable cause) {
		super(message, code, source, cause);
	}

	public JobDispatcherException(ErrorType errorType) {
		super(errorType);
	}

	public JobDispatcherException(ErrorType errorType, Throwable cause) {
		super(errorType, cause);
	}

	public JobDispatcherException(ErrorType errorType, Class<?> source) {
		super(errorType, source);
	}

	public JobDispatcherException(ErrorType errorType, Class<?> source, Throwable cause) {
		super(errorType, source, cause);
	}

	public JobDispatcherException(Throwable cause) {
		super(cause);
	}

}
