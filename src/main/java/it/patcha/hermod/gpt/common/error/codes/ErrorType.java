package it.patcha.hermod.gpt.common.error.codes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This enumeration collects error codes and related error messages. */
public enum ErrorType {

	/** Main Usage Message: Output text which explains command line format */
	UI01("UI", 1,
			"Usage: java -jar hermod-gippity.jar -connF <Connection Factory URL> -requestQ <Request Queue Name> [-text <Text Here> | -file <File Path>]"),

	/** JobDispatcherException: Parameter null */
	JD01("JD", 1, "dispatcherInput parameter cannot be null"),
	/** JobDispatcherException: Wrong parameter type */
	JD02("JD", 2, "dispatcherInput should be an instance of %s class, we got %s"),

	/** InfoReaderException: Parameter null */
	IR01("IR", 1, "readerInput parameter cannot be null"),
	/** InfoReaderException: Wrong parameter type */
	IR02("IR", 2, "readerInput should be an instance of %s class, we got %s"),
	/** InfoReaderException: Unknown argument */
	IR03("IR", 3, "Unknown argument: %s"),
	/** InfoReaderException: Failed to load value for argument */
	IR04("IR", 4, "Failed to load value for argument: %s"),

	/** WorkflowManagerException: Parameter null */
	WM01("WM", 1, "workflowInput parameter cannot be null"),
	/** WorkflowManagerException: Wrong parameter type */
	WM02("WM", 2, "workflowInput should be an instance of %s class, we got %s"),
	/** WorkflowManagerException: Failed to send message */
	WM03("WM", 3, "Error sending message to queue: %s"),

	/** Generic IOException: Error reading file */
	IO01("IO", 1, "Error reading file: %s"),

	/** Generic RuntimeException: Error reading file */
	RE99("RE", 99, "Unexpected error on runtime"),

	/** Test Error: to not be used in runtime code */
	TT01("TT", 1, "Error message for test");

	private final String codePrefix;
	private final int codeNumber;
	private final String message;

	private static final Map<Integer, List<ErrorType>> byCodeNumber = new HashMap<>();
	private static final Map<String, List<ErrorType>> byCodePrefix = new HashMap<>();
	private static final Map<String, ErrorType> byCode = new HashMap<>();
	private static final Map<String, ErrorType> byMessage = new HashMap<>();

	/* Initialize all maps. */
	static {
		for (ErrorType type : ErrorType.values()) {
			if (!byCodeNumber.containsKey(type.codeNumber))
				byCodeNumber.put(type.codeNumber, new ArrayList<>());
			byCodeNumber.get(type.codeNumber).add(type);

			if (!byCodePrefix.containsKey(type.codePrefix))
				byCodePrefix.put(type.codePrefix, new ArrayList<>());
			byCodePrefix.get(type.codePrefix).add(type);

			byCode.put(type.getCode(), type);
			byMessage.put(type.message, type);
		}
	}

	/** Get all ErrorType into a list, by corresponding integer part of the code
	 * @param codeNumber : the integer part of the code for the wanted list of ErrorType
	 * @throws IllegalArgumentException if no corresponding ErrorType is found
	 * @return corresponding list of ErrorType if found, otherwise casts IllegalArgumentException
	 */
	public static List<ErrorType> fromCodeNumber(int codeNumber) {
		List<ErrorType> type = byCodeNumber.get(codeNumber);

		if (type == null)
			throw new IllegalArgumentException("Invalid data type: " + codeNumber);

		return type;
	}

	/** Get all ErrorType into a list, by corresponding prefix of the code
	 * @param codeNumber : the prefix of the code for the wanted list of ErrorType
	 * @throws IllegalArgumentException if no corresponding ErrorType is found
	 * @return corresponding list of ErrorType if found, otherwise casts IllegalArgumentException
	 */
	public static List<ErrorType> fromCodePrefix(String codeNumber) {
		List<ErrorType> type = byCodePrefix.get(codeNumber);

		if (type == null)
			throw new IllegalArgumentException("Invalid data type: " + codeNumber);

		return type;
	}

	ErrorType(String codePrefix, int codeNumber, String message) {
		this.codeNumber = codeNumber;
		this.codePrefix = codePrefix;
		this.message = message;
	}

	/** Get the ErrorType by corresponding code
	 * @param code : the code for the wanted ErrorType
	 * @throws IllegalArgumentException if no corresponding ErrorType is found
	 * @return corresponding ErrorType if found, otherwise casts IllegalArgumentException
	 */
	public static ErrorType fromCode(String code) {
		ErrorType type = byCode.get(code);

		if (type == null)
			throw new IllegalArgumentException("Invalid data type: " + code);

		return type;
	}

	/** Get the ErrorType by corresponding message
	 * @param message : the message for the wanted ErrorType
	 * @throws IllegalArgumentException if no corresponding ErrorType is found
	 * @return corresponding ErrorType if found, otherwise casts IllegalArgumentException
	 */
	public static ErrorType fromMessage(String message) {
		ErrorType type = byMessage.get(message);

		if (type == null)
			throw new IllegalArgumentException("Invalid data type: " + message);

		return type;
	}

	public String getCodePrefix() {
		return codePrefix;
	}

	public int getCodeNumber() {
		return codeNumber;
	}

	public String getCode() {
		return String.format("%s%02d", codePrefix, codeNumber);
	}

	public String getMessage() {
		return message;
	}

}
