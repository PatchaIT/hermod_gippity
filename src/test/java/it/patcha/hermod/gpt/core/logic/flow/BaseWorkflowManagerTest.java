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

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;

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
	void testHandleWorkflow_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodBean result = workflowManager.handleWorkflow(sendBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalseToLog(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHandleWorkflow_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + WorkflowManagerException.class.getName());
			logger.debug(getStartTestLog());

			WorkflowManagerException exception =
					assertThrowsToLog(WorkflowManagerException.class, () -> workflowManager.handleWorkflow(null), getEndTestLogKO());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of BaseWorkflowManager used only for test purposes */
	private static class BaseWorkflowManagerImpl extends BaseWorkflowManager {
	}

}