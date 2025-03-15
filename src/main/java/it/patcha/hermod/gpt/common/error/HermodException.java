package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import lombok.Getter;
import lombok.Synchronized;

import java.io.Serial;

import static it.patcha.hermod.gpt.common.constant.HermodConstants.UNKNOWN_CLASS;

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
@Getter
public class HermodException extends Exception {

	@Serial
	private static final long serialVersionUID = 8300442580806755165L;

	public static final String MESSAGE_MASK = "%s [%s%s]%s : %s";

	private final String message;
	private final String code;
	private final Class<?> source;
	@Getter(onMethod = @__(@Synchronized))
	private final Throwable cause;

	public HermodException() {
		this.message = null;
		this.code = null;
		this.source = null;
		this.cause = null;
	}

	public <T extends HermodException> HermodException(T child) {
		this.message = child.getMessage();
		this.code = child.getCode();
		this.source = child.getSource();
		this.cause = child;
	}

	public HermodException(String message) {
		super(message);
		this.message = message;
		this.code = null;
		this.source = null;
		this.cause = null;
	}

	public HermodException(Class<?> source) {
		this.message = null;
		this.code = null;
		this.source = source;
		this.cause = null;
	}

	public HermodException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.code = null;
		this.source = null;
		this.cause = cause;
	}

	public HermodException(String message, String code) {
		super(message);
		this.message = message;
		this.code = code;
		this.source = null;
		this.cause = null;
	}

	public HermodException(String message, Class<?> source) {
		super(message);
		this.message = message;
		this.code = null;
		this.source = source;
		this.cause = null;
	}

	public HermodException(String message, String code, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.code = code;
		this.source = null;
		this.cause = cause;
	}

	public HermodException(String message, String code, Class<?> source) {
		super(message);
		this.message = message;
		this.code = code;
		this.source = source;
		this.cause = null;
	}

	public HermodException(String message, String code, Class<?> source, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.code = code;
		this.source = source;
		this.cause = cause;
	}

	public HermodException(ErrorType errorType) {
		super(errorType.getMessage());
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
		this.source = null;
		this.cause = null;
	}

	public HermodException(ErrorType errorType, Throwable cause) {
		super(errorType.getMessage(), cause);
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
		this.source = null;
		this.cause = cause;
	}

	public HermodException(ErrorType errorType, Class<?> source) {
		super(errorType.getMessage());
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
		this.source = source;
		this.cause = null;
	}

	public HermodException(ErrorType errorType, Class<?> source, Throwable cause) {
		super(errorType.getMessage(), cause);
		this.message = errorType.getMessage();
		this.code = errorType.getCode();
		this.source = source;
		this.cause = cause;
	}

	public HermodException(Throwable cause) {
		super(cause);
		this.message = null;
		this.code = null;
		this.source = null;
		this.cause = cause;
	}

	@Override
	public String toString() {
		return String.format(MESSAGE_MASK,
				this.getClass().getName(),
				source != null ? source.getSimpleName() : UNKNOWN_CLASS,
				cause != null ? " - " + cause.getClass().getSimpleName() : "",
				code != null ? " " + code : "",
				message);
	}

	public String throwableToString() {
		return super.toString();
	}

}
