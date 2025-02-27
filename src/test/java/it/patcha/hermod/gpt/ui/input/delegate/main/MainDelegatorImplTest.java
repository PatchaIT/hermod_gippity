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
	void testCastToArgsBean_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = delegator.castToArgsBean(argsBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testCastToArgsBean_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			logger.debug(getStartTestLog());

			DelegatorException exception =
					assertThrowsToLog(DelegatorException.class, () -> delegator.castToArgsBean(sendBean), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateArgs_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(argsBean).when(argInfoReader).handleOptions(argsBean);

			ArgsBean result = delegator.delegateArgs(argInfoReader, argsBean);
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateArgs_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			logger.debug(getStartTestLog());

			doThrow(new InfoReaderException(errorCause)).when(argInfoReader).handleOptions(argsBean);
			DelegatorException exception =
					assertThrowsToLog(DelegatorException.class, () -> delegator.delegateArgs(argInfoReader, argsBean), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateMessage_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(sendBean).when(messageSender).handleWorkflow(any(SendBean.class));

			argsBean.setConnectionFactoryUrl(valConnectionFactoryUrl);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getConnectionFactory(argsBean.getConnectionFactoryUrl())).thenReturn(connectionFactory);

				SendBean result = delegator.delegateMessage(messageSender, argsBean);
				assertNotNullToLog(result, getEndTestLogKO());

				logger.debug("{}{}", getEndTestLogOK(), result);
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testDelegateMessage_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + DelegatorException.class.getName());
			logger.debug(getStartTestLog());

			doThrow(new WorkflowManagerException(errorCause)).when(messageSender).handleWorkflow(any(SendBean.class));

			argsBean.setConnectionFactoryUrl(valConnectionFactoryUrl);
			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getConnectionFactory(argsBean.getConnectionFactoryUrl())).thenReturn(connectionFactory);

				DelegatorException exception =
						assertThrowsToLog(DelegatorException.class, () -> delegator.delegateMessage(messageSender, argsBean), getEndTestLogKO());

				logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}