package it.patcha.hermod.gpt.ui.input.read.common.error;

import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;

import java.io.Serial;

/** Main exception type for InfoReader error management. */
public class InfoReaderException extends HermodException {

	@Serial
	private static final long serialVersionUID = -2705294333138024660L;

	public InfoReaderException() {
	}

	public <T extends HermodException> InfoReaderException(T child) {
		super(child);
	}

	public InfoReaderException(String message) {
		super(message);
	}

	public InfoReaderException(Class<?> source) {
		super(source);
	}

	public InfoReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfoReaderException(String message, String code) {
		super(message, code);
	}

	public InfoReaderException(String message, Class<?> source) {
		super(message, source);
	}

	public InfoReaderException(String message, String code, Throwable cause) {
		super(message, code, cause);
	}

	public InfoReaderException(String message, String code, Class<?> source) {
		super(message, code, source);
	}

	public InfoReaderException(String message, String code, Class<?> source, Throwable cause) {
		super(message, code, source, cause);
	}

	public InfoReaderException(ErrorType errorType) {
		super(errorType);
	}

	public InfoReaderException(ErrorType errorType, Throwable cause) {
		super(errorType, cause);
	}

	public InfoReaderException(ErrorType errorType, Class<?> source) {
		super(errorType, source);
	}

	public InfoReaderException(ErrorType errorType, Class<?> source, Throwable cause) {
		super(errorType, source, cause);
	}

	public InfoReaderException(Throwable cause) {
		super(cause);
	}

}
