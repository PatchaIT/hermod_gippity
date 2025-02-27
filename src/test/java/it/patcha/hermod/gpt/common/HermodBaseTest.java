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
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.Serial;

import static it.patcha.hermod.gpt.common.constant.HermodConstants.Args;
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

/** Abstract class with base tests for all application unit tests which must extend this. */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class HermodBaseTest extends AssertWrapper {
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
	protected final String empty = HermodConstants.EMPTY;
	protected final String argGui = Args.GUI.toString();
	protected final String argConnectionFactoryUrl = Args.CONNECTION_FACTORY_URL.toString();
	protected final String valConnectionFactoryUrl = "tcp://localhost:61616";
	protected final ConnectionFactory objConnectionFactory = new ActiveMQConnectionFactory(valConnectionFactoryUrl);
	protected final String argRequestQueueName = Args.REQUEST_QUEUE_NAME.toString();
	protected final String valRequestQueueName = "testRequestQueue";
	protected final String argReplyQueueName = Args.REPLY_QUEUE_NAME.toString();
	protected final String valReplyQueueName = "testReplyQueue";
	protected final String argJmsMessageText = Args.JMS_MESSAGE_TEXT.toString();
	protected final String valJmsMessageText = "Hello, World!";
	protected final String argJmsMessageFilePath = Args.JMS_MESSAGE_FILE_PATH.toString();
	protected final String valJmsMessageFilePath = "message.test";
	protected final File objJmsMessageFile = new File(valJmsMessageFilePath);
	protected final ErrorType errorType = TT01;
	protected final String errorCode = "TT01";
	protected final String errorMessage = "Error message for test";
	protected final TestHermodException errorCause = new TestHermodException(errorType, new TestHermodException(errorMessage));
	protected final Exception errorThrowable = new RuntimeException(errorMessage, new RuntimeException(errorMessage));

	// Test attributes
	@Autowired
	protected ApplicationContext context;

	protected boolean first = true;

	protected HermodTestInfo hermodTestInfo;

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

			assertTrueToLog(true, getEndTestLog(TEST_KO_ABS) +
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

			assertNotNullToLog(context, getEndTestLog(TEST_KO_ABS) +
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
		assertDoesNotThrowToLog(() -> verify(appender, atLeastOnce()), getEndTestLogKO()).doAppend(logCaptor.capture());

		return logCaptor.getAllValues().stream()
				.anyMatch(event ->
						level == event.getLevel() &&
						message.equals(event.getFormattedMessage()));
	}

	/** An extension of HermodException used only for test purposes */
	public static class TestHermodException extends HermodException {
		@Serial
		private static final long serialVersionUID = 7295198888273346093L;
		public TestHermodException() {}
		public TestHermodException(String message) {super(message);}
		public TestHermodException(ErrorType errorType, Throwable cause) {
			super(errorType.getMessage(), errorType.getCode(), cause);
		}
	}

}
