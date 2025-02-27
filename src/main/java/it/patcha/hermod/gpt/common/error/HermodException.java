package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;

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
@Getter
@Setter
public class HermodException extends Exception {

	@Serial
	private static final long serialVersionUID = 8300442580806755165L;

	private String message;
	private String code;
	@Getter(onMethod = @__(@Synchronized))
	@Setter(onMethod = @__(@Synchronized))
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

	@Override
	public String toString() {
		return getClass().getName() + ": " + (StringUtils.isEmpty(this.code) ? this.code + ": " + this.message : this.message);
	}

	public String throwableToString() {
		return super.toString();
	}

}
