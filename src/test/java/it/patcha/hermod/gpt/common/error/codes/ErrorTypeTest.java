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
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_EMPTY;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ErrorTypeTest extends HermodBaseTest {

	@Test
	void testFromCodeNumber(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			List<ErrorType> result = ErrorType.fromCodeNumber(ERROR_TYPE.getCodeNumber());
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(EXP_NOT_EMPTY.toString());
			assertFalse(result.isEmpty(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS.toString() + ERROR_TYPE);
			assertTrue(result.contains(ERROR_TYPE), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), EXP_CONTAINS, result.get(result.indexOf(ERROR_TYPE)));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCodePrefix(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			List<ErrorType> result = ErrorType.fromCodePrefix(ERROR_TYPE.getCodePrefix());
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(EXP_NOT_EMPTY.toString());
			assertFalse(result.isEmpty(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS.toString() + ERROR_TYPE);
			assertTrue(result.contains(ERROR_TYPE), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), EXP_CONTAINS, result.get(result.indexOf(ERROR_TYPE)));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ErrorType result = ErrorType.fromCode(ERROR_TYPE.getCode());
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_TYPE.toString());
			assertEquals(ERROR_TYPE, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFromMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ErrorType result = ErrorType.fromMessage(ERROR_TYPE.getMessage());
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_TYPE.toString());
			assertEquals(ERROR_TYPE, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

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

			String result = ERROR_TYPE.getCodePrefix();
			assertNotNull(result, getEndTestLogKO());

			String expected = ERROR_CODE.substring(0, 2);
			swapInfoExpected(expected);
			assertEquals(expected, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testGetCodeNumber(TestInfo testInfo) throws Exception {
		try {
			int expected = Integer.decode(ERROR_CODE.substring(2));

			enrichTestInfo(testInfo, Integer.toString(expected));
			logger.debug(getStartTestLog());

			int result = ERROR_TYPE.getCodeNumber();
			assertEquals(expected, result, getEndTestLogKO());

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

			String result = ERROR_TYPE.getCode();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result, getEndTestLogKO());

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

			String result = ERROR_TYPE.getMessage();
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}