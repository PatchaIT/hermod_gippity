package it.patcha.hermod.gpt.common.error.codes;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_CONTAINS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_EMPTY;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.INVALID_DATA_TYPE;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ErrorTypeTest extends HermodBaseTest {

	public static final String ERROR_CODE_PREFIX_WRONG = "ZZZ";
	public static final int ERROR_CODE_NUMBERS_WRONG = 999;
	public static final String ERROR_CODE_WRONG = ERROR_CODE_PREFIX_WRONG + ERROR_CODE_NUMBERS_WRONG;
	public static final String ERROR_MESSAGE_WRONG = "Wrong error message for test";

	@Test
	void testFromCodeNumber_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			List<ErrorType> result = ErrorType.fromCodeNumber(errorType.getCodeNumber());
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_NOT_EMPTY.toString());
			assertFalseToLog(result.isEmpty(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS.toString() + errorType);
			assertTrueToLog(result.contains(errorType), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), EXP_CONTAINS, result.get(result.indexOf(errorType)));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCodeNumber_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + IllegalArgumentException.class.getName());
			logger.debug(getStartTestLog());

			IllegalArgumentException exception =
					assertThrowsToLog(IllegalArgumentException.class, () -> ErrorType.fromCodeNumber(ERROR_CODE_NUMBERS_WRONG), getEndTestLogKO());

			swapInfoExpected(INVALID_DATA_TYPE + ERROR_CODE_NUMBERS_WRONG);
			assertEqualsToLog(INVALID_DATA_TYPE + ERROR_CODE_NUMBERS_WRONG, exception.getMessage(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCodePrefix_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			List<ErrorType> result = ErrorType.fromCodePrefix(errorType.getCodePrefix());
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_NOT_EMPTY.toString());
			assertFalseToLog(result.isEmpty(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS.toString() + errorType);
			assertTrueToLog(result.contains(errorType), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), EXP_CONTAINS, result.get(result.indexOf(errorType)));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCodePrefix_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + IllegalArgumentException.class.getName());
			logger.debug(getStartTestLog());

			IllegalArgumentException exception =
					assertThrowsToLog(IllegalArgumentException.class, () -> ErrorType.fromCodePrefix(ERROR_CODE_PREFIX_WRONG), getEndTestLogKO());

			swapInfoExpected(INVALID_DATA_TYPE + ERROR_CODE_PREFIX_WRONG);
			assertEqualsToLog(INVALID_DATA_TYPE + ERROR_CODE_PREFIX_WRONG, exception.getMessage(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ErrorType result = ErrorType.fromCode(errorType.getCode());
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorType.toString());
			assertEqualsToLog(errorType, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCode_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + IllegalArgumentException.class.getName());
			logger.debug(getStartTestLog());

			IllegalArgumentException exception =
					assertThrowsToLog(IllegalArgumentException.class, () -> ErrorType.fromCode(ERROR_CODE_WRONG), getEndTestLogKO());

			swapInfoExpected(INVALID_DATA_TYPE + ERROR_CODE_WRONG);
			assertEqualsToLog(INVALID_DATA_TYPE + ERROR_CODE_WRONG, exception.getMessage(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromMessage_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ErrorType result = ErrorType.fromMessage(errorType.getMessage());
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorType.toString());
			assertEqualsToLog(errorType, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromMessage_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + IllegalArgumentException.class.getName());
			logger.debug(getStartTestLog());

			IllegalArgumentException exception =
					assertThrowsToLog(IllegalArgumentException.class, () -> ErrorType.fromMessage(ERROR_MESSAGE_WRONG), getEndTestLogKO());

			swapInfoExpected(INVALID_DATA_TYPE + ERROR_MESSAGE_WRONG);
			assertEqualsToLog(INVALID_DATA_TYPE + ERROR_MESSAGE_WRONG, exception.getMessage(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetCodePrefix(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = errorType.getCodePrefix();
			assertNotNullToLog(result, getEndTestLogKO());

			String expected = errorCode.substring(0, 2);
			swapInfoExpected(expected);
			assertEqualsToLog(expected, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetCodeNumber(TestInfo testInfo) throws Exception {
		try {
			int expected = Integer.decode(errorCode.substring(2));

			enrichTestInfo(testInfo, Integer.toString(expected));
			logger.debug(getStartTestLog());

			int result = errorType.getCodeNumber();
			assertEqualsToLog(expected, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = errorType.getCode();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = errorType.getMessage();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}