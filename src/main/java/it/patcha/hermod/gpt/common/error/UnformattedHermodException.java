package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/**
 * Usually it is expected that error messages into this application's Exception
 *   are formatted in a particular way.
 * <p/>
 * This Exception class is used in context where no formatting is applied to
 *   the error message (i.e.: in static methods).
 * It should emphasize the need to format it later, when used in non-static scopes.
 */
public class UnformattedHermodException extends HermodException {

	@Serial
	private static final long serialVersionUID = -2840729156326416096L;

	public UnformattedHermodException() {
	}

	public <T extends HermodException> UnformattedHermodException(T child) {
		this(child.getMessage(), child.getCode(), child);
	}

	public UnformattedHermodException(String message) {
		super(message);
	}

	public UnformattedHermodException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnformattedHermodException(String message, String code) {
		super(message, code);
	}

	public UnformattedHermodException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public UnformattedHermodException(ErrorType errorType) {
		super(errorType.getMessage(), errorType.getCode());
	}

	public UnformattedHermodException(ErrorType errorType, Throwable cause) {
		super(errorType.getMessage(), errorType.getCode(), cause);
	}

	public UnformattedHermodException(Throwable cause) {
		super(cause);
	}

}
