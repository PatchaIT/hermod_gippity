package it.patcha.hermod.gpt.ui.input.delegate.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for Delegator error management. */
public class DelegatorException extends HermodException {

	@Serial
	private static final long serialVersionUID = -3859484707662217446L;

	public DelegatorException() {
	}

	public <T extends HermodException> DelegatorException(T child) {
		super(child);
	}

	public DelegatorException(String message) {
		super(message);
	}

	public DelegatorException(Class<?> source) {
		super(source);
	}

	public DelegatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public DelegatorException(String message, String code) {
		super(message, code);
	}

	public DelegatorException(String message, Class<?> source) {
		super(message, source);
	}

	public DelegatorException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public DelegatorException(String message, String code, Class<?> source) {
		super(message, code, source);
	}

	public DelegatorException(String message, String code, Class<?> source, Throwable cause) {
		super(message, code, source, cause);
	}

	public DelegatorException(ErrorType errorType) {
		super(errorType);
	}

	public DelegatorException(ErrorType errorType, Throwable cause) {
		super(errorType, cause);
	}

	public DelegatorException(ErrorType errorType, Class<?> source) {
		super(errorType, source);
	}

	public DelegatorException(ErrorType errorType, Class<?> source, Throwable cause) {
		super(errorType, source, cause);
	}

	public DelegatorException(Throwable cause) {
		super(cause);
	}

}
