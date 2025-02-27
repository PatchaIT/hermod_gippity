package it.patcha.hermod.gpt;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NO_EXCEPTION;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodGippityTest extends HermodBaseTest {

	public static final String OUTCOME_POSITIVE = "0";
	public static final String OUTCOME_NEGATIVE = "1";
	public static final String OUTCOME_EXCEPTION = "-1";
	public static final String LOG_FOUND = "log found = ";

	private HermodGippity hermodGippity;

	@Mock
	private HermodGippity mockHermodGippity;

	@Mock
	private ApplicationContext mockContext;

	@Mock
	private MainJobDispatcher mainJobDispatcher;

	@Mock
	private Appender<ILoggingEvent> logAppender;

	private String[] args = null;
	private HermodBean argsBean;
	private HermodBean sendBean;

	@BeforeEach
	void setUp() {
		args = new String[]{
				argConnectionFactoryUrl, valConnectionFactoryUrl,
				argRequestQueueName, valRequestQueueName,
				argJmsMessageText, valJmsMessageText,
				argJmsMessageFilePath, valJmsMessageFilePath
		};

		argsBean = new ArgsBean(args);
		sendBean = new SendBean();
		sendBean.setSuccessful(true);

		hermodGippity = new HermodGippity(mainJobDispatcher);
	}

	@Test
	void testMain_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, LOG_FOUND + EXP_TRUE);
			logger.debug(getStartTestLog());

			Logger rootLogger = (Logger) LoggerFactory.getLogger(HermodGippity.class);
			rootLogger.addAppender(logAppender);

			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getContext(SpringConfig.class)).thenReturn(mockContext);
				doReturn(mockHermodGippity).when(mockContext).getBean(HermodGippity.class);

				doReturn(0).when(mockHermodGippity).run(args);
				HermodGippity.main(args);

				boolean result = checkIntoLogs(HermodConstants.MAIN_POSITIVE_LOG, Level.INFO, logAppender);
				assertTrueToLog(result, getEndTestLogOK());
				logger.debug("{}{}", getEndTestLogOK(), result);

				swapInfoExpected(EXP_NO_EXCEPTION.toString());
				assertDoesNotThrowToLog(() -> hermodUtils.verify(() -> HermodUtils.getContext(SpringConfig.class)), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrowToLog(() -> verify(mockContext).getBean(HermodGippity.class), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrowToLog(() -> verify(mockHermodGippity).run(args), getEndTestLog(TEST_AND_KO));

				logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testMain_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, LOG_FOUND + EXP_TRUE);
			logger.debug(getStartTestLog());

			Logger rootLogger = (Logger) LoggerFactory.getLogger(HermodGippity.class);
			rootLogger.addAppender(logAppender);

			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getContext(SpringConfig.class)).thenReturn(mockContext);
				doReturn(mockHermodGippity).when(mockContext).getBean(HermodGippity.class);

				doReturn(1).when(mockHermodGippity).run(args);
				HermodGippity.main(args);

				boolean result = checkIntoLogs(HermodConstants.MAIN_NEGATIVE_LOG, Level.INFO, logAppender);
				assertTrueToLog(result, getEndTestLogOK());
				logger.debug("{}{}", getEndTestLogOK(), result);

				swapInfoExpected(EXP_NO_EXCEPTION.toString());
				assertDoesNotThrowToLog(() -> hermodUtils.verify(() -> HermodUtils.getContext(SpringConfig.class)), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrowToLog(() -> verify(mockContext).getBean(HermodGippity.class), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrowToLog(() -> verify(mockHermodGippity).run(args), getEndTestLog(TEST_AND_KO));

				logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testRun_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, OUTCOME_POSITIVE);
			logger.debug(getStartTestLog());

			when(mainJobDispatcher.handleJobs(argsBean)).thenReturn(sendBean);

			int result = hermodGippity.run(args);
			assertEqualsToLog(OUTCOME_POSITIVE, Integer.toString(result), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_NO_EXCEPTION.toString());
			assertDoesNotThrowToLog(() -> verify(mainJobDispatcher).handleJobs(argsBean), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testRun_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, OUTCOME_NEGATIVE);
			logger.debug(getStartTestLog());

			sendBean.setSuccessful(false);
			when(mainJobDispatcher.handleJobs(argsBean)).thenReturn(sendBean);

			int result = hermodGippity.run(args);
			assertEqualsToLog(OUTCOME_NEGATIVE, Integer.toString(result), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			when(mainJobDispatcher.handleJobs(argsBean)).thenReturn(null);

			result = hermodGippity.run(args);
			assertEqualsToLog(OUTCOME_NEGATIVE, Integer.toString(result), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			swapInfoExpected(EXP_NO_EXCEPTION.toString());
			assertDoesNotThrowToLog(() -> verify(mainJobDispatcher, times(2)).handleJobs(argsBean), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testRun_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, OUTCOME_EXCEPTION);
			logger.debug(getStartTestLog());

			doThrow(new JobDispatcherException(errorMessage, errorCause)).when(mainJobDispatcher).handleJobs(argsBean);

			int result = hermodGippity.run(args);
			assertEqualsToLog(OUTCOME_EXCEPTION, Integer.toString(result), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			doThrow(new RuntimeException(errorMessage, errorCause)).when(mainJobDispatcher).handleJobs(argsBean);

			result = hermodGippity.run(args);
			assertEqualsToLog(OUTCOME_EXCEPTION, Integer.toString(result), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

			swapInfoExpected(EXP_NO_EXCEPTION.toString());
			assertDoesNotThrowToLog(() -> verify(mainJobDispatcher, times(2)).handleJobs(argsBean), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}
