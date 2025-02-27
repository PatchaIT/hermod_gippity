package it.patcha.hermod.gpt.common;

import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class AssertWrapper extends HermodTestClass {

	// Core code methods

	/**
	 * Designed to provide a wrapper for {@code void} {@link Assertions}
	 *   static methods, which will eventually log the AssertionError if cast.
	 * </p>
	 * Possible use:
	 * {@code logAssertError(Assertions::assertNotNull, actual, message);},
	 *   where the second parameter can be {@code (String) null} if unwanted.
	 *
	 * @param function lambda call to static assertion method from {@link Assertions}
	 * @param actual what you want to be checked for the assertion
	 * @param message the failure message to supply and log
	 * @param <T> the type of want to be checked for the assertion
	 * @param <U> the type of the failure message to supply and log
	 */
	private <T, U> void logAssertionError(BiConsumer<T, U> function, T actual, U message) {
		try {
			function.accept(actual, message);

		} catch (AssertionError ae) {
			logger.debug(message.toString(), ae);
			throw ae;
		}
	}

	/**
	 * Designed to provide a wrapper for {@code void} {@link Assertions}
	 *   static methods with three parameters, which will eventually
	 *   log the AssertionError if cast.
	 * </p>
	 * Possible use:
	 * {@code logAssertError(Assertions::assertEquals, expected, actual, message);},
	 *   where the third parameter can be {@code (String) null} if unwanted.
	 *
	 * @param function lambda call to static assertion method from {@link Assertions}
	 * @param expected what do you expect to be {@code actual}
	 * @param actual what you want to be checked for the assertion
	 * @param message the failure message to supply and log
	 * @param <T> the type of want you want to compare to {@code actual}
	 * @param <U> the type of want to be checked for the assertion
	 * @param <V> the type of the failure message to supply and log
	 */
	private <T, U, V> void logAssertionError(TriConsumer<T, U, V> function, T expected, U actual, V message) {
		try {
			function.accept(expected, actual, message);

		} catch (AssertionError ae) {
			logger.debug(message.toString(), ae);
			throw ae;
		}
	}

	/**
	 * Designed to provide a wrapper for {@code void} {@link Assertions}
	 *   static methods, which will eventually log the AssertionError if cast.
	 * </p>
	 * Possible use:
	 * {@code logAssertError(Assertions::assertDoesNotThrow, actual, message);},
	 *   where the second parameter can be {@code (String) null} if unwanted.
	 *
	 * @param function lambda call to static assertion method from {@link Assertions}
	 * @param actual what you want to be checked for the assertion
	 * @param message the failure message to supply and log
	 * @param <T> the type of want to be checked for the assertion
	 * @param <U> the type of the failure message to supply and log
	 */
	private <T, U, R> R logAssertionErrorReturn(BiFunction<T, U, R> function, T actual, U message) {
		try {
			return function.apply(actual, message);

		} catch (AssertionError ae) {
			logger.debug(message.toString(), ae);
			throw ae;
		}
	}

	/**
	 * Designed to provide a wrapper for {@code void} {@link Assertions}
	 *   static methods with three parameters, which will eventually
	 *   log the AssertionError if cast.
	 * </p>
	 * Possible use:
	 * {@code logAssertError(Assertions::assertNotNull, actual, message);},
	 *   where the third parameter can be {@code (String) null} if unwanted.
	 *
	 * @param function lambda call to static assertion method from {@link Assertions}
	 * @param expected what do you expect to be {@code actual}
	 * @param actual what you want to be checked for the assertion
	 * @param message the failure message to supply and log
	 * @param <T> the type of want you want to compare to {@code actual}
	 * @param <U> the type of want to be checked for the assertion
	 * @param <V> the type of the failure message to supply and log
	 */
	private <T, U, V, R> R logAssertionErrorReturn(TriFunction<T, U, V, R> function, T expected, U actual, V message) {
		try {
			return function.apply(expected, actual, message);

		} catch (AssertionError ae) {
			logger.debug(message.toString(), ae);
			throw ae;
		}
	}

	// Assert wrapper methods

	/**
	 * <em>Assert</em> that the supplied {@code condition} is {@code true}.
	 * <p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param condition the boolean to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code condition} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertTrueToLog(boolean condition, String message) {
		logAssertionError(Assertions::assertTrue, condition, concatToMessage(condition, message));
	}

	/**
	 * <em>Assert</em> that the supplied {@code condition} is {@code false}.
	 * <p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param condition the boolean to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code condition} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertFalseToLog(boolean condition, String message) {
		logAssertionError(Assertions::assertFalse, condition, concatToMessage(condition, message));
	}

	/**
	 * <em>Assert</em> that {@code actual} is {@code null}.
	 * <p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param actual the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code actual} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertNullToLog(Object actual, String message) {
		logAssertionError(Assertions::assertNull, actual, concatToMessage(actual, message));
	}

	/**
	 * <em>Assert</em> that {@code actual} is not {@code null}.
	 * <p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param actual the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code actual} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertNotNullToLog(Object actual, String message) {
		logAssertionError(Assertions::assertNotNull, actual, concatToMessage(actual, message));
	}

	/**
	 * <em>Assert</em> that {@code expected} and {@code actual} are equal.
	 * <p>
	 * If both are {@code null}, they are considered equal.
	 * </p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * <p>
	 * Failure message will also be sent to log.
	 * </p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param expected what do you expect to be {@code actual}
	 * @param actual the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code actual} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertEqualsToLog(Object expected, Object actual, String message) {
		logAssertionError(Assertions::assertEquals, expected, actual, concatToMessage(actual, message));
	}

	/**
	 * <em>Assert</em> that {@code expected} and {@code actual} are not equal.
	 * <p>
	 * Fails if both are {@code null}.
	 * </p>
	 * Fails with the supplied failure {@code message + actual}.
	 * If {@code message} is empty, default failure message will be used.
	 * <p>
	 * Failure message will also be sent to log.
	 * </p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + actual}.
	 *
	 * @param expected what do you expect to be {@code actual}
	 * @param actual the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code actual} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertNotEqualsToLog(Object expected, Object actual, String message) {
		logAssertionError(Assertions::assertNotEquals, expected, actual, concatToMessage(actual, message));
	}

	/**
	 * <em>Assert</em> that execution of the supplied {@code executable} throws
	 *   an exception of the {@code expectedType} and return the exception.
	 * <p>
	 * Fails with the supplied failure {@code message + supplier}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + supplier}.
	 *
	 * @param expectedType the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code supplier} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 * @return the instance returned by the {@code supplier}
	 * @param <T> the type of returned object
	 */
	protected <T extends Throwable> T assertThrowsToLog(Class<T> expectedType, Executable executable, String message) {
		return logAssertionErrorReturn(Assertions::assertThrows, expectedType, executable, concatToMessage(executable, message));
	}

	/**
	 * <em>Assert</em> that execution of the supplied {@code supplier} does
	 * <em>not</em> throw any kind of {@link Throwable}.
	 * <p>
	 * Fails with the supplied failure {@code message + supplier}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + supplier}.
	 *
	 * @param supplier the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code supplier} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 * @return the instance returned by the {@code supplier}
	 * @param <T> the type of returned object
	 */
	protected <T> T assertDoesNotThrowToLog(ThrowingSupplier<T> supplier, String message) {
		return logAssertionErrorReturn(Assertions::assertDoesNotThrow, supplier, concatToMessage(supplier, message));
	}

	/**
	 * <em>Assert</em> that execution of the supplied {@code executable} does
	 * <em>not</em> throw any kind of {@link Throwable}.
	 * <p>
	 * Fails with the supplied failure {@code message + executable}.
	 * If {@code message} is empty, default failure message will be used.
	 * </p>
	 * Failure message will also be sent to log.
	 * <p>
	 * In case of AssertionError, it will also add the stacktrace
	 *   to {@code message + executable}.
	 *
	 * @param executable the object to check for the assertion
	 * @param message the failure message to supply and log,
	 *   {@code executable} will be appended to the message;
	 *   if {@code message} is empty, default failure message will be adopted
	 *   and logged, instead.
	 */
	protected void assertDoesNotThrowToLog(Executable executable, String message) {
		logAssertionError(Assertions::assertDoesNotThrow, executable, concatToMessage(executable, message));
	}

}
