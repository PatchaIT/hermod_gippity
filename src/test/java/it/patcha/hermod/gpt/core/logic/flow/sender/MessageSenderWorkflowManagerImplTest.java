package it.patcha.hermod.gpt.core.logic.flow.sender;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.task.sender.MessageSenderTaskExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MessageSenderWorkflowManagerImplTest extends HermodBaseTest {

	@InjectMocks
	private MessageSenderWorkflowManagerImpl workflowManager;

	@Mock
	private MessageSenderTaskExecutor messageSenderTaskExecutor;

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

			doReturn(sendBean).when(messageSenderTaskExecutor).castToSendBean(sendBean);
			doReturn(sendBean).when(messageSenderTaskExecutor).readFile(sendBean);
			doReturn(sendBean).when(messageSenderTaskExecutor).sendMessage(sendBean);

			HermodBean result = workflowManager.handleWorkflow(sendBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalse(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result.isSuccessful());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}