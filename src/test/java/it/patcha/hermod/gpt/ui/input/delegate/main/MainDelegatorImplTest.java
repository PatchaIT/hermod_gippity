package it.patcha.hermod.gpt.ui.input.delegate.main;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.core.logic.flow.common.error.WorkflowManagerException;
import it.patcha.hermod.gpt.core.logic.flow.sender.MessageSenderWorkflowManager;
import it.patcha.hermod.gpt.ui.input.delegate.common.error.DelegatorException;
import it.patcha.hermod.gpt.ui.input.read.args.ArgInfoReader;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;
import jakarta.jms.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MainDelegatorImplTest extends HermodBaseTest {

	@InjectMocks
	private MainDelegatorImpl delegator;

	@Mock
	private ArgInfoReader argInfoReader;

	@Mock
	private MessageSenderWorkflowManager messageSender;

	@Mock
	private ConnectionFactory connectionFactory;

	private ArgsBean argsBean;
	private SendBean sendBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();
		sendBean = new SendBean();
	}

	@Test
	void testCastToArgsBean(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = delegator.castToArgsBean(argsBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			DelegatorException exception =
					assertThrows(DelegatorException.class, () -> delegator.castToArgsBean(sendBean), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateArgs(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(argsBean).when(argInfoReader).handleOptions(argsBean);

			ArgsBean result = delegator.delegateArgs(argInfoReader, argsBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			doThrow(new InfoReaderException(ERROR_CAUSE)).when(argInfoReader).handleOptions(argsBean);
			DelegatorException exception =
					assertThrows(DelegatorException.class, () -> delegator.delegateArgs(argInfoReader, argsBean), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateMessage(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(sendBean).when(messageSender).handleWorkflow(any(SendBean.class));

			argsBean.setConnectionFactoryUrl(CONNECTION_FACTORY_URL);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getConnectionFactory(argsBean.getConnectionFactoryUrl())).thenReturn(connectionFactory);

				SendBean result = delegator.delegateMessage(messageSender, argsBean);
				assertNotNull(result, getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), result);

				swapInfoExpected(EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
				doThrow(new WorkflowManagerException(ERROR_CAUSE)).when(messageSender).handleWorkflow(any(SendBean.class));
				DelegatorException exception =
						assertThrows(DelegatorException.class, () -> delegator.delegateMessage(messageSender, argsBean), getEndTestLog(TEST_AND_KO));

				logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}