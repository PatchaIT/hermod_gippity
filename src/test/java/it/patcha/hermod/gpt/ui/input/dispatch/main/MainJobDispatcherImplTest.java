package it.patcha.hermod.gpt.ui.input.dispatch.main;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.flow.sender.MessageSenderWorkflowManager;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import it.patcha.hermod.gpt.ui.input.delegate.main.MainDelegator;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MainJobDispatcherImplTest extends HermodBaseTest {

	private MainJobDispatcherImpl jobDispatcher;

	@Mock
	private MainDelegator mainDelegator;

	@Mock
	private ArgInfoReader argInfoReader;

	@Mock
	private MessageSenderWorkflowManager messageSender;

	private ArgsBean argsBean;
	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();
		sendBean = new SendBean();

		jobDispatcher = new MainJobDispatcherImpl(mainDelegator, argInfoReader, messageSender);
	}

	@Test
	void testHandleJobs_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(argsBean).when(mainDelegator).castToArgsBean(argsBean);
			doReturn(argsBean).when(mainDelegator).delegateArgs(argInfoReader, argsBean);
			doReturn(sendBean).when(mainDelegator).delegateMessage(messageSender, argsBean);

			HermodBean result = jobDispatcher.handleJobs(argsBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalseToLog(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result.isSuccessful());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHandleJobs_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + JobDispatcherException.class.getName());
			logger.debug(getStartTestLog());

			doThrow(new DelegatorException(errorCause)).when(mainDelegator).castToArgsBean(argsBean);

			JobDispatcherException exception =
					assertThrowsToLog(JobDispatcherException.class, () -> jobDispatcher.handleJobs(argsBean), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}