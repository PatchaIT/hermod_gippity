package it.patcha.hermod.gpt.core.logic.flow.common.error;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class WorkflowManagerExceptionTest extends HermodBaseTest {

	@Test
	void testConstructorEmpty(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException();
			assertNull(result.getCode(), getEndTestLogKO());
			assertNull(result.getMessage(), getEndTestLogKO());
			assertNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorChild(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_CAUSE);
			assertNotNull(result.getCode(), getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_MESSAGE);
			assertNull(result.getCode(), getEndTestLogKO());
			assertNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNull(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_MESSAGE, ERROR_CAUSE);
			assertNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCode(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_MESSAGE, ERROR_CODE);
			assertNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorMessageCodeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_MESSAGE, ERROR_CODE, ERROR_CAUSE);
			assertNotNull(result.getCode(), getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorErrorType(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_TYPE);
			assertNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorErrorTypeCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_TYPE, ERROR_CAUSE);
			assertNotNull(result.getCode(), getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_MESSAGE);
			assertEquals(ERROR_MESSAGE, result.getMessage(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testConstructorCause(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = new WorkflowManagerException(ERROR_THROWABLE);
			assertNull(result.getCode(), getEndTestLogKO());
			assertNull(result.getMessage(), getEndTestLogKO());

			swapInfoExpected(EXP_NOT_NULL.toString());
			assertNotNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}