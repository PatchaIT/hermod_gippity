package it.patcha.hermod.gpt.core.logic.flow.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for WorkflowManager error management. */
public class WorkflowManagerException extends HermodException {

	@Serial
	private static final long serialVersionUID = -983522808887640910L;

	public WorkflowManagerException() {
	}

	public <T extends HermodException> WorkflowManagerException(T child) {
		this(child.getMessage(), child.getCode(), child);
	}

	public WorkflowManagerException(String message) {
		super(message);
	}

	public WorkflowManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkflowManagerException(String message, String code) {
		super(message, code);
	}

	public WorkflowManagerException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public WorkflowManagerException(ErrorType errorType) {
		super(errorType.getMessage(), errorType.getCode());
	}

	public WorkflowManagerException(ErrorType errorType, Throwable cause) {
		super(errorType.getMessage(), errorType.getCode(), cause);
	}

	public WorkflowManagerException(Throwable cause) {
		super(cause);
	}

}
