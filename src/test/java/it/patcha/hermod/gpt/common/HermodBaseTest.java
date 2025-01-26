package it.patcha.hermod.gpt.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import it.patcha.hermod.gpt.common.bean.HermodTestInfo;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.constant.HermodConstants.BackgroundColor;
import it.patcha.hermod.gpt.common.constant.HermodConstants.TextColor;
import it.patcha.hermod.gpt.common.error.HermodException;
import it.patcha.hermod.gpt.common.error.codes.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.Serial;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_CLASS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_DEF;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_GO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_GO_ABS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_KO_ABS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_OK;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_OK_ABS;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.TT01;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/** Base abstract class to be father of all application unit tests. */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class HermodBaseTest {
	// Test delimiters' templates
	/** To format as: {@code String.format(LOG_TEMPLATE, Separator)} */
	public static final String LOG_TEMPLATE = " %s ";
	/** Resets all colours after, to format as: {@code String.format(LOG_TEMPLATE_RA, TextColor, BackgroundColor, Separator)} */
	public static final String LOG_TEMPLATE_RA = "%s%s %s " + HermodConstants.RESET_ALL;
	public static final String LOG_SEPARATOR = ">>>";
	public static final String LOG_SEPARATOR_AND = "+++";
	public static final String LOG_SEPARATOR_CLASS = "#####";
	public static final String LOG_NL = " ... " + HermodConstants.LOG_NL;

	/**
	 *  Contains enumerations for:
	 *  <p>
	 *  * "Test delimiters" (starting by {@code TEST_})
	 *  </p>
	 *  * "Test common expected results" (starting by {@code EXP_})
	 */
	public enum TestOutcome {
		// Test delimiters (colors are always set explicitly to override any IDE custom color)
		TEST_CLASS(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.CYAN, LOG_SEPARATOR_CLASS)),
		TEST_GO(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.CYAN, LOG_SEPARATOR)),
		TEST_GO_ABS(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.BLUE, LOG_SEPARATOR)),
		TEST_OK(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.GREEN, LOG_SEPARATOR)),
		TEST_AND_OK(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.GREEN, LOG_SEPARATOR_AND)),
		TEST_OK_ABS(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.DARK_GREEN, LOG_SEPARATOR)),
		TEST_KO(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.RED, LOG_SEPARATOR)),
		TEST_AND_KO(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.RED, LOG_SEPARATOR_AND)),
		TEST_KO_ABS(String.format(LOG_TEMPLATE_RA, TextColor.BLACK, BackgroundColor.MAGENTA, LOG_SEPARATOR)),
		TEST_DEF(String.format(LOG_TEMPLATE, LOG_SEPARATOR)),

		// Test common expected results
		EXP_TRUE(HermodConstants.STR_TRUE),
		EXP_FALSE(HermodConstants.STR_FALSE),
		EXP_NOT_NULL("not null"),
		EXP_NULL("null"),
		EXP_NOT_EMPTY("not empty"),
		EXP_EMPTY("empty"),
		EXP_CONTAINS("contains "),
		EXP_NO_EXCEPTION("no Exception"),
		EXP_EXCEPTION("Exception type");

		// Enum's attribute, constructor and toString
		private final String string;
		TestOutcome(String string) {this.string = string;}
		@Override public String toString() {return string;}
	}

	// Test common logs
	public static final String LOG_TEST_CLASS = "{} Starting tests for class: {}";
	public static final String LOG_TEST_START = "%s Start %s";
	public static final String LOG_TEST_END = " End %s: Expected %s, we got = ";

	// Test default logs
	public static final String LOG_DEF_TITLE = "next test";
	public static final String LOG_DEF_OUTCOME = "End current test:%s we got = ";
	public static final String LOG_DEF_EXPECTED = " Expected %s,";

	// Common use test values (not static for comfort reasons)
	protected final String EMPTY = HermodConstants.EMPTY;
	protected final String ARG_CONNECTION_FACTORY_URL = HermodConstants.ARG_CONNECTION_FACTORY_URL;
	protected final String CONNECTION_FACTORY_URL = "tcp://localhost:61616";
	protected final String ARG_REQUEST_QUEUE_NAME = HermodConstants.ARG_REQUEST_QUEUE_NAME;
	protected final String REQUEST_QUEUE_NAME = "testRequestQueue";
	protected final String ARG_REPLY_QUEUE_NAME = HermodConstants.ARG_REPLY_QUEUE_NAME;
	protected final String REPLY_QUEUE_NAME = "testReplyQueue";
	protected final String ARG_JMS_MESSAGE_TEXT = HermodConstants.ARG_JMS_MESSAGE_TEXT;
	protected final String JMS_MESSAGE_TEXT = "Hello, World!";
	protected final String ARG_JMS_MESSAGE_FILE_PATH = HermodConstants.ARG_JMS_MESSAGE_FILE_PATH;
	protected final String JMS_MESSAGE_FILE_PATH = "message.test";
	protected final File JMS_MESSAGE_FILE = new File(JMS_MESSAGE_FILE_PATH);
	protected final ErrorType ERROR_TYPE = TT01;
	protected final String ERROR_CODE = "TT01";
	protected final String ERROR_MESSAGE = "Error message for test";
	protected final TestHermodException ERROR_CAUSE = new TestHermodException(ERROR_TYPE, new TestHermodException(ERROR_MESSAGE));
	protected final Exception ERROR_THROWABLE = new RuntimeException(ERROR_MESSAGE, new RuntimeException(ERROR_MESSAGE));

	// Test attributes
	@Autowired
	protected ApplicationContext context;

	protected Logger logger;
	protected boolean first = true;

	protected HermodTestInfo hermodTestInfo;

	public HermodBaseTest() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@BeforeEach
	void setUpBeforeAll() {
		if (first) {
			logger.info(LOG_TEST_CLASS, TEST_CLASS, this.getClass().getSimpleName());
			first = false;
		}
	}

	@Test
	void testFramework(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog(TEST_GO_ABS.toString()));

			assertTrue(true, getEndTestLog(TEST_KO_ABS) +
					TextColor.MAGENTA + "; ❌ test framework is not up and running." + HermodConstants.RESET_ALL);

			logger.debug("{}{}{}{}{}", getEndTestLog(TEST_OK_ABS), true,
					TextColor.DARK_GREEN, "; ✅ test framework is up and running.", HermodConstants.RESET_ALL);

		} catch (Exception ex) {
			logger.error("{}{}{}", getEndTestLog(TEST_KO_ABS), "❌ ", ex.getClass().getSimpleName(), ex);
			throw ex;
		}
	}

	@Test
	void testContextLoads(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog(TEST_GO_ABS.toString()));

			assertNotNull(context, getEndTestLog(TEST_KO_ABS) +
					TextColor.MAGENTA + " ❌ test context load failed." + HermodConstants.RESET_ALL);

			logger.debug("{}{}{}{}{}{}", getEndTestLog(TEST_OK_ABS),
					TextColor.DARK_GREEN, "✅ ", context.getClass().getSimpleName(),
					"; ✅ test context loaded.", HermodConstants.RESET_ALL);

		} catch (Exception ex) {
			logger.error("{}{}{}", getEndTestLog(TEST_KO_ABS), "❌ ", ex.getClass().getSimpleName(), ex);
			throw ex;
		}
	}

	// Utility methods

	/**
	 * Stores info about current test method,
	 *   which will be used by automated log builder methods.
	 * <p>
	 * In case of null parameter/s, {@code LOG_DEF_} constants
	 *   values will be adopted where necessary.
	 *
	 * @param testInfo jupiter's {@link TestInfo} object.
	 * To get it, just add the parameter {@code (TestInfo testInfo)}
	 *   to your test method
	 * @param expected the expected result: when applicable, we solicit the use
	 *   of {@link TestOutcome#toString()} from common expected results enum,
	 *   those starting with {@code EXP_}
	 */
	protected void enrichTestInfo(TestInfo testInfo, String expected) {
		hermodTestInfo = new HermodTestInfo(testInfo, expected);
	}

	/**
	 * Swaps the {@code expected} part into current test class's
	 *   {@link HermodTestInfo#getOutcome()}.
	 *
	 * @param expected the new expected result for current test
	 * @see HermodTestInfo
	 */
	protected void swapInfoExpected(String expected) {
		if (hermodTestInfo == null)
			return;

		hermodTestInfo.swapInfoExpected(expected);
	}

	/**
	 * Generates a standard test method start log text.
	 * <p>
	 * In case of necessity, {@code LOG_DEF_} constants
	 *   value will be adopted.
	 *
	 * @return the text for a standard test method start log
	 */
	protected String getStartTestLog() {
		return getStartTestLog(null);
	}

	/**
	 * Generates a standard test method start log text,
	 *   adopting supplied {@code separator}.
	 * <p>
	 * In case of necessity, {@code LOG_DEF_} constants
	 *   value will be adopted.
	 *
	 * @return the text for a standard test method start log,
	 * 	 adopting supplied {@code separator}.
	 */
	protected String getStartTestLog(String separator) {
		if (StringUtils.isEmpty(separator))
			separator = TEST_GO.toString();

		String title = hermodTestInfo != null ?
				hermodTestInfo.getDisplayName() :
				LOG_DEF_TITLE;
		return String.format(LOG_TEST_START, separator, title);
	}

	/**
	 * Generates a standard test method ending log text,
	 *   with outcome info.
	 * <p>
	 * In case of necessity, {@code LOG_DEF_} constants
	 *   value will be adopted.
	 *
	 * @param outDelimiter {@link TestOutcome} delimiter enum,
	 *   those starting with {@code TEST_}, to be chosen based
	 *   on success or failure
	 * @return the text for a standard test method end log,
	 * 	 with outcome info
	 */
	protected String getEndTestLog(TestOutcome outDelimiter) {
		String outcome = outDelimiter != null ?
				outDelimiter.toString() :
				TEST_DEF.toString();

		outcome += hermodTestInfo != null ?
				hermodTestInfo.getOutcome() :
				String.format(LOG_DEF_OUTCOME, "");

		return outcome;
	}

	/**
	 * Generates a standard test method ending log text,
	 *   with success info.
	 *
	 * @return the text for a standard successful test log
	 */
	protected String getEndTestLogOK() {
		return getEndTestLog(TEST_OK);
	}

	/**
	 * Generates a standard test method ending log text,
	 *   with fail info.
	 *
	 * @return the text for a standard failed test log
	 */
	protected String getEndTestLogKO() {
		return getEndTestLog(TEST_KO);
	}

	/**
	 * Concat {@code message.toString()} with {@code actual.toString()},
	 *   returning {@code message} + {@code actual}.
	 * </p>
	 * If {@code message} is {@code empty},
	 *   then {@code null} will be returned.
	 *
	 * @param concat what you want to concatenate to {@code message}
	 * @param message the message after which {@code actual} have to be concatenated
	 * @return {@code message} + {@code actual}, if {@code message} is not {@code empty},
	 *   otherwise {@code null}
	 * @param <T> the type of {@code actual},
	 *   {@code actual.toString()} will be concatenated
	 */
	protected <T, U> String concatToMessage(T concat, U message) {
		return !ObjectUtils.isEmpty(message) ?
				message.toString() + (
						!ObjectUtils.isEmpty(concat) ? concat : "") :
				"";
	}

	/**
	 * Given an {@code appender} already added to the right {@link ch.qos.logback.classic.Logger}
	 *   instance, this method allows to check if the {@code message} was logged
	 *   at the right logging {@code level}.
	 *
	 * @param message the message to check into logs
	 * @param level the level of the {@code message} to check into logs
	 * @param appender the {@link Appender} instance already added to
	 *   the right {@link ch.qos.logback.classic.Logger}
	 * @return {@code true} if {@code message} was found into logs,
	 *   otherwise {@code false}
	 * @see Appender
	 * @see ch.qos.logback.classic.Logger
	 */
	protected boolean checkIntoLogs(String message, Level level, Appender<ILoggingEvent> appender) {
		ArgumentCaptor<ILoggingEvent> logCaptor = ArgumentCaptor.forClass(ILoggingEvent.class);
		assertDoesNotThrow(() -> verify(appender, atLeastOnce()), getEndTestLogKO()).doAppend(logCaptor.capture());

		return logCaptor.getAllValues().stream()
				.anyMatch(event ->
						level == event.getLevel() &&
						message.equals(event.getFormattedMessage()));
	}

	// Assertions wrappers

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
	protected void assertTrue(boolean condition, String message) {
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
	protected void assertFalse(boolean condition, String message) {
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
	protected void assertNull(Object actual, String message) {
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
	protected void assertNotNull(Object actual, String message) {
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
	protected void assertEquals(Object expected, Object actual, String message) {
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
	protected void assertNotEquals(Object expected, Object actual, String message) {
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
	protected <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message) {
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
	protected <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, String message) {
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
	protected void assertDoesNotThrow(Executable executable, String message) {
		logAssertionError(Assertions::assertDoesNotThrow, executable, concatToMessage(executable, message));
	}

	/** An extension of HermodException used only for test purposes */
	public static class TestHermodException extends HermodException {
		@Serial private static final long serialVersionUID = 7295198888273346093L;
		public TestHermodException() {}
		public TestHermodException(String message) {super(message);}
		public TestHermodException(ErrorType errorType, Throwable cause) {
			super(errorType.getMessage(), errorType.getCode(), cause);
		}
	}

}
