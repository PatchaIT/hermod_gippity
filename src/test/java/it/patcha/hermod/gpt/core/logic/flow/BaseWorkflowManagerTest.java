package it.patcha.hermod.gpt.core.logic.flow;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_CONTAINS;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class BaseWorkflowManagerTest extends HermodBaseTest {

	@InjectMocks
	private BaseWorkflowManagerImpl workflowManager;

	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		sendBean = new SendBean();
	}

	@Test
	void testHandleWorkflow(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodBean result = workflowManager.handleWorkflow(sendBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalse(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			WorkflowManagerException exception =
					assertThrows(WorkflowManagerException.class, () -> workflowManager.handleWorkflow(null), getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testFormatWorkflowManagerException(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = workflowManager.formatWorkflowManagerException(ERROR_MESSAGE, ERROR_CODE);
			assertNotNull(result, getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCause(), getEndTestLogKO());

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

			result = workflowManager.formatWorkflowManagerException(ERROR_MESSAGE, ERROR_CAUSE);
			assertNotNull(result, getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCode(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			result = workflowManager.formatWorkflowManagerException(ERROR_MESSAGE);
			assertNotNull(result, getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCode(), getEndTestLog(TEST_AND_OK));
			assertNull(result.getCause(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(EXP_CONTAINS + ERROR_MESSAGE);
			assertTrue(result.getMessage().contains(ERROR_MESSAGE), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			result = workflowManager.formatWorkflowManagerException(ERROR_TYPE, ERROR_CAUSE);
			assertNotNull(result, getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(ERROR_CAUSE.toString());
			assertEquals(ERROR_CAUSE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + ERROR_TYPE.getMessage());
			assertTrue(result.getMessage().contains(ERROR_TYPE.getMessage()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

			result = workflowManager.formatWorkflowManagerException(ERROR_TYPE);
			assertNotNull(result, getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getCode(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCause(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + ERROR_TYPE.getMessage());
			assertTrue(result.getMessage().contains(ERROR_TYPE.getMessage()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testIncludeAndFormatIntoWorkflowManagerException(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			WorkflowManagerException result = workflowManager.includeAndFormatIntoWorkflowManagerException(ERROR_THROWABLE, ERROR_CODE);
			assertNotNull(result, getEndTestLogKO());
			assertNotNull(result.getMessage(), getEndTestLogKO());
			assertNotNull(result.getCause(), getEndTestLogKO());
			assertNotNull(result.getCode(), getEndTestLogKO());

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(ERROR_CODE);
			assertEquals(ERROR_CODE, result.getCode(), getEndTestLogKO());

			swapInfoExpected(EXP_CONTAINS + ERROR_THROWABLE.getMessage());
			assertTrue(result.getMessage().contains(ERROR_THROWABLE.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, result.toString());

			result = workflowManager.includeAndFormatIntoWorkflowManagerException(ERROR_THROWABLE);
			assertNotNull(result, getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getMessage(), getEndTestLog(TEST_AND_OK));
			assertNotNull(result.getCause(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(EXP_NULL.toString());
			assertNull(result.getCode(), getEndTestLog(TEST_AND_OK));

			swapInfoExpected(ERROR_THROWABLE.toString());
			assertEquals(ERROR_THROWABLE, result.getCause(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_CONTAINS + ERROR_THROWABLE.getMessage());
			assertTrue(result.getMessage().contains(ERROR_THROWABLE.getMessage()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, result.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of BaseWorkflowManager used only for test purposes */
	private static class BaseWorkflowManagerImpl extends BaseWorkflowManager {
	}

}