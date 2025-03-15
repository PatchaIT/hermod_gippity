package it.patcha.hermod.gpt.common.error;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EQUALS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.UNKNOWN_CLASS;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodExceptionTest extends HermodBaseTest {

	private static final String TO_THROWABLE_STRING = "%s: %s";

	@Test
	void testGetCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorMessage, errorCode);
			String result = hermodException.getCode();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result, getEndTestLog(TEST_AND_KO));

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

			HermodException hermodException = new HermodException(errorMessage);
			String result = hermodException.getMessage();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorCause);
			Throwable result = hermodException.getCause();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + errorCause.getClass().getName());
			assertEqualsToLog(errorCause, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_Empty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException();
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getSource(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithChild(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage);
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getSource(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithSource(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(this.getClass());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessageCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, errorCause);
			assertNullToLog(result.getSource(), getEndTestLogKO());
			assertNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessageCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, errorCode);
			assertNullToLog(result.getSource(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessageSource(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, this.getClass());
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessageCodeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, errorCode, errorCause);
			assertNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithMessageCodeSource(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, errorCode, this.getClass());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_Full(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorMessage, errorCode, this.getClass(), errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorType(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorType);
			assertNullToLog(result.getSource(), getEndTestLogKO());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorTypeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorType, errorCause);
			assertNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorTypeSource(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorType, this.getClass());
			assertNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithErrorTypeSourceCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorType, this.getClass(), errorCause);
			assertNotNullToLog(result.getCode(), getEndTestLogKO());
			assertNotNullToLog(result.getMessage(), getEndTestLogKO());
			assertNotNullToLog(result.getSource(), getEndTestLogKO());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorCode);
			assertEqualsToLog(errorCode, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorMessage);
			assertEqualsToLog(errorMessage, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(getSimpleName(this));
			assertEqualsToLog(this.getClass(), result.getSource(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(errorCause.toString());
			assertEqualsToLog(errorCause, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructor_WithCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException result = new HermodException(errorThrowable);
			assertNullToLog(result.getCode(), getEndTestLogKO());
			assertNullToLog(result.getMessage(), getEndTestLogKO());
			assertNullToLog(result.getSource(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNullToLog(result.getCause(), getEndTestLogKO());

			swapInfoExpected(errorThrowable.toString());
			assertEqualsToLog(errorThrowable, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + HermodException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString_Empty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException();
			String toString = String.format(
					HermodException.MESSAGE_MASK,
					hermodException.getClass().getName(),
					UNKNOWN_CLASS,
					"",
					"",
					null
			);

			String result = hermodException.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toString);
			assertEqualsToLog(toString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString_MessageOnly(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorType.getMessage());
			String toString = String.format(
					HermodException.MESSAGE_MASK,
					hermodException.getClass().getName(),
					UNKNOWN_CLASS,
					"",
					"",
					errorType.getMessage()
			);

			String result = hermodException.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toString);
			assertEqualsToLog(toString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString_MessageCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorType);
			String toString = String.format(
					HermodException.MESSAGE_MASK,
					hermodException.getClass().getName(),
					UNKNOWN_CLASS,
					"",
					" " + errorType.getCode(),
					errorType.getMessage()
			);

			String result = hermodException.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toString);
			assertEqualsToLog(toString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString_NoCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorType, this.getClass());
			String toString = String.format(
					HermodException.MESSAGE_MASK,
					hermodException.getClass().getName(),
					this.getClass().getSimpleName(),
					"",
					" " + errorType.getCode(),
					errorType.getMessage()
			);

			String result = hermodException.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toString);
			assertEqualsToLog(toString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString_Full(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorType, this.getClass(), errorThrowable);
			String toString = String.format(
					HermodException.MESSAGE_MASK,
					hermodException.getClass().getName(),
					this.getClass().getSimpleName(),
					" - " + errorThrowable.getClass().getSimpleName(),
					" " + errorType.getCode(),
					errorType.getMessage()
			);

			String result = hermodException.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toString);
			assertEqualsToLog(toString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testThrowableToString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodException hermodException = new HermodException(errorType, this.getClass(), errorThrowable);
			String toThrwoableString = String.format(TO_THROWABLE_STRING, hermodException.getClass().getName(), hermodException.getMessage());

			String result = hermodException.throwableToString();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(EXP_EQUALS + LOG_NL + toThrwoableString);
			assertEqualsToLog(toThrwoableString, result, getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}