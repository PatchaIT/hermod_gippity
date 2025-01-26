package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/**
 * Base application Exception.
 * <p/>
 * Compared to a traditional Exception:
 * <ul>
 *   <li>Includes an additional field: {@code code}.</li>
 *   <li>Shows all possible constructor, including some with {@link ErrorType}.</li>
 *   <li>Implements setters for {@code message}, {@code code} and {@code cause},
 *     to allow to instantiate with generic constructor and add details later.</li>
 * </ul>
 */
public class HermodException extends Exception {

	@Serial
	private static final long serialVersionUID = 8300442580806755165L;

	private String message;
	private String code;
	private Throwable cause;

	public HermodException() {
	}

	public <T extends HermodException> HermodException(T child) {
		this(child.getMessage(), child.getCode(), child);
	}

	public HermodException(String message) {
		super(message);
		this.message = message;
	}

	public HermodException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
	}

	public HermodException(String message, String code) {
		super(message);
		this.message = message;
		this.code = code;
	}

	public HermodException(String message, String code, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.code = code;
		this.cause = cause;
	}

	public HermodException(ErrorType errorType) {
		super(errorType.getMessage());
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
	}

	public HermodException(ErrorType errorType, Throwable cause) {
		super(errorType.getMessage(), cause);
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
		this.cause = cause;
	}

	public HermodException(Throwable cause) {
		super(cause);
		this.cause = cause;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public String toString() {
		String s = getClass().getName();
		String message = code != null ? code + ": " + this.message : this.message;
		return message != null ? s + ": " + message : s;
	}

	public String throwableToString() {
		return super.toString();
	}

}
