package it.patcha.hermod.gpt.ui.input.dispatch;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
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
class BaseJobDispatcherTest extends HermodBaseTest {

	@InjectMocks
	private BaseJobDispatcherImpl jobDispatcher;

	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();
	}

	@Test
	void testHandleJobs_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			HermodBean result = jobDispatcher.handleJobs(argsBean);
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
	void testHandleJobs_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + JobDispatcherException.class.getName());
			logger.debug(getStartTestLog());

			JobDispatcherException exception =
					assertThrowsToLog(JobDispatcherException.class, () -> jobDispatcher.handleJobs(null), getEndTestLogKO());
			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of BaseJobDispatcher used only for test purposes */
	private static class BaseJobDispatcherImpl extends BaseJobDispatcher {
	}

}