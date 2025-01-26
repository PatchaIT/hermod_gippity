package it.patcha.hermod.gpt.ui.input.verify.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for TaskExecutor error management. */
public class ValidatorException extends HermodException {

	@Serial
	private static final long serialVersionUID = -3859484707662217446L;

	public ValidatorException() {
	}

	public <T extends HermodException> ValidatorException(T child) {
		this(child.getMessage(), child.getCode(), child);
	}

	public ValidatorException(String message) {
		super(message);
	}

	public ValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidatorException(String message, String code) {
		super(message, code);
	}

	public ValidatorException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public ValidatorException(ErrorType errorType) {
		super(errorType.getMessage(), errorType.getCode());
	}

	public ValidatorException(ErrorType errorType, Throwable cause) {
		super(errorType.getMessage(), errorType.getCode(), cause);
	}

	public ValidatorException(Throwable cause) {
		super(cause);
	}

}
