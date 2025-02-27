package it.patcha.hermod.gpt.common.constant;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

/** This class keeps application constants with a general scope. */
public class HermodConstants {

	/** HashCode odd primary integer collection */
	private static final int[] INITIAL_NON_ZERO_ODD_NUMBERS = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
	private static final int[] MULTIPLIER_NON_ZERO_ODD_NUMBERS = ArrayUtils.clone(INITIAL_NON_ZERO_ODD_NUMBERS);
	static { ArrayUtils.reverse(MULTIPLIER_NON_ZERO_ODD_NUMBERS); }

	/** Command Line Args */
	public enum Args {
		GUI ("-guy"),
		CONNECTION_FACTORY_URL ("-connF"),
		REQUEST_QUEUE_NAME ("-requestQ"),
		REPLY_QUEUE_NAME ("-replyQ"),
		JMS_MESSAGE_TEXT ("-text"),
		JMS_MESSAGE_FILE_PATH ("-file");

		// Enum's attribute, constructor and toString
		private final String string;
		Args(String string) {this.string = string;}
		@Override public String toString() {return string;}

		// Enum's search by value
		private static final Map<String, Args> byString = new HashMap<>();
		static { for (Args arg : Args.values()) { byString.put(arg.string, arg); } }
		public static Args fromString(String string) {
			Args arg = byString.get(string);
			if (arg == null) throw new IllegalArgumentException(INVALID_DATA_TYPE + string);
			return arg;
		}
	}

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
	public static final String UNKNOWN_CLASS = "Unknown class";
	public static final String MAIN_POSITIVE_LOG = "Positively ended.";
	public static final String MAIN_NEGATIVE_LOG = "Ended with issues, please check logs.";
	public static final String MESSAGE_SENT_LOG = "Message sent successfully to queue: {}";
	public static final String OUTCOME_POSITIVE = "Done and done, with args: {}";
	public static final String OUTCOME_NEGATIVE = "Something went wrong, please check logs, with args: {}";
	public static final String OUTCOME_ERROR = "{} : {} : {}";

	// Enums
	public static final String INVALID_DATA_TYPE = "Invalid data type: ";

	private HermodConstants() {}

	public static int[] getInitialNonZeroOddNumbers() {
		return INITIAL_NON_ZERO_ODD_NUMBERS;
	}

	public static int[] getMultiplierNonZeroOddNumbers() {
		return MULTIPLIER_NON_ZERO_ODD_NUMBERS;
	}

}
