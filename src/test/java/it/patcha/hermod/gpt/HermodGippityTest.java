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
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class HermodGippityTest extends HermodBaseTest {

	@InjectMocks
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
				ARG_CONNECTION_FACTORY_URL, CONNECTION_FACTORY_URL,
				ARG_REQUEST_QUEUE_NAME, REQUEST_QUEUE_NAME,
				ARG_JMS_MESSAGE_TEXT, JMS_MESSAGE_TEXT,
				ARG_JMS_MESSAGE_FILE_PATH, JMS_MESSAGE_FILE_PATH
		};

		argsBean = new ArgsBean(args);
		sendBean = new SendBean();
		sendBean.setSuccessful(true);
	}

	@Test
	void testMain(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, "log found = " + EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			Logger rootLogger = (Logger) LoggerFactory.getLogger(HermodGippity.class);
			rootLogger.addAppender(logAppender);

			try (MockedStatic<HermodUtils> hermodUtils = mockStatic(HermodUtils.class)) {
				hermodUtils.when(() -> HermodUtils.getContext(SpringConfig.class)).thenReturn(mockContext);
				doReturn(mockHermodGippity).when(mockContext).getBean(HermodGippity.class);

				doReturn(0).when(mockHermodGippity).run(args);
				HermodGippity.main(args);

				boolean result = checkIntoLogs(HermodConstants.MAIN_POSITIVE_LOG, Level.INFO, logAppender);
				assertTrue(result, getEndTestLogOK());
				logger.debug("{}{}", getEndTestLogOK(), result);

				doReturn(1).when(mockHermodGippity).run(args);
				HermodGippity.main(args);

				result = checkIntoLogs(HermodConstants.MAIN_NEGATIVE_LOG, Level.INFO, logAppender);
				assertTrue(result, getEndTestLog(TEST_AND_OK));
				logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

				swapInfoExpected(EXP_NO_EXCEPTION.toString());
				assertDoesNotThrow(() -> hermodUtils.verify(() -> HermodUtils.getContext(SpringConfig.class), times(2)), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrow(() -> verify(mockContext, times(2)).getBean(HermodGippity.class), getEndTestLog(TEST_AND_KO));
				assertDoesNotThrow(() -> verify(mockHermodGippity, times(2)).run(args), getEndTestLog(TEST_AND_KO));
				logger.debug("{}{}", getEndTestLog(TEST_AND_OK), EXP_NO_EXCEPTION);
			}

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testRun(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, "1");
			logger.debug(getStartTestLog());

			when(mainJobDispatcher.handleJobs(argsBean)).thenReturn(sendBean);

			int result = hermodGippity.run(args);

			assertDoesNotThrow(() -> verify(mainJobDispatcher).handleJobs(argsBean), getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}
