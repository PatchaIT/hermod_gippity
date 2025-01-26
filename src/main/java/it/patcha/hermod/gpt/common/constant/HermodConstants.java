package it.patcha.hermod.gpt.common.constant;

import org.apache.commons.lang3.ArrayUtils;

/** This class keeps application constants with a general scope. */
public class HermodConstants {

	/** HashCode odd primary integer collection */
	public static final int[] initialNonZeroOddNumbers = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
	public static final int[] multiplierNonZeroOddNumbers = ArrayUtils.clone(initialNonZeroOddNumbers);
	static { ArrayUtils.reverse(multiplierNonZeroOddNumbers); }

	// Command Line Args Constants
	public static final String ARG_CONNECTION_FACTORY_URL = "-connF";
	public static final String ARG_REQUEST_QUEUE_NAME = "-requestQ";
	public static final String ARG_REPLY_QUEUE_NAME = "-replyQ";
	public static final String ARG_JMS_MESSAGE_TEXT = "-text";
	public static final String ARG_JMS_MESSAGE_FILE_PATH = "-file";

	/** Contains enumeration for console text colors */
	public enum TextColor {
		// Console text colors
		BLACK("\u001B[30m"),
		RED("\u001B[31m"),
		GREEN("\u001B[32m"),
		YELLOW("\u001B[33m"),
		BLUE("\u001B[34m"),
		MAGENTA("\u001B[35m"),
		CYAN("\u001B[36m"),
		WHITE("\u001B[37m"),
		DARK_GREEN("\u001B[38;5;28m");

		// Enum's attribute, constructor and toString
		private final String string;
		TextColor(String string) {this.string = string;}
		@Override public String toString() {return string;}
	}

	/** Contains enumeration for console background colors */
	public enum BackgroundColor {
		// Console background colors
		BLACK("\u001B[40m"),
		RED("\u001B[41m"),
		GREEN("\u001B[42m"),
		YELLOW("\u001B[43m"),
		BLUE("\u001B[44m"),
		MAGENTA("\u001B[45m"),
		CYAN("\u001B[46m"),
		WHITE("\u001B[47m"),
		DARK_GREEN("\u001B[48;5;28m");

		// Enum's attribute, constructor and toString
		private final String string;
		BackgroundColor(String string) {this.string = string;}
		@Override public String toString() {return string;}
	}

	// Console reset all colors
	public static final String RESET_ALL = "\u001B[0m";

	// General utility constants
	public static final String EMPTY = "";
	public static final String STR_TRUE = Boolean.TRUE.toString();
	public static final String STR_FALSE = Boolean.FALSE.toString();
	public static final String LOG_NL = "\n";

	// Log messages
	public static final String MAIN_POSITIVE_LOG = "Positively ended.";
	public static final String MAIN_NEGATIVE_LOG = "Ended with issues, please check logs.";
	public static final String MESSAGE_SENT_LOG = "Message sent successfully to queue: {}";

	private HermodConstants() {}

}
