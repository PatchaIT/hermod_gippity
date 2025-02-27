package it.patcha.hermod.gpt.common.bean.ui.input;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.Arrays;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ArgsBeanTest extends HermodBaseTest {

	private static final String TO_STRING_EMPTY = "[args=<null>,messageText=<null>,messageFile=<null>,requestQueueName=<null>,replyQueueName=<null>,connectionFactoryUrl=<null>,successful=false]";

	private String[] args = null;
	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		args = new String[]{
				argConnectionFactoryUrl, valConnectionFactoryUrl,
				argRequestQueueName, valRequestQueueName,
				argJmsMessageText, valJmsMessageText,
				argJmsMessageFilePath, valJmsMessageFilePath
		};

		argsBean = new ArgsBean();
	}

	@Test
	void testSetGetArgs(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setArgs(args);
			String[] result = argsBean.getArgs();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected("... \n" + Arrays.toString(args));
			assertEqualsToLog(args, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), "\n" + Arrays.toString(result));

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetMessageText(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageText(valJmsMessageText);
			String result = argsBean.getMessageText();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetMessageFile(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageFile(objJmsMessageFile);
			File result = argsBean.getMessageFile();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(objJmsMessageFile.toString());
			assertEqualsToLog(objJmsMessageFile, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetMessageFilePath(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setMessageFilePath(valJmsMessageFilePath);
			File result = argsBean.getMessageFile();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(objJmsMessageFile.toString());
			assertEqualsToLog(objJmsMessageFile, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetRequestQueueName(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setRequestQueueName(valRequestQueueName);
			String result = argsBean.getRequestQueueName();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetReplyQueueName(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setReplyQueueName(valReplyQueueName);
			String result = argsBean.getReplyQueueName();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valReplyQueueName);
			assertEqualsToLog(valReplyQueueName, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testSetGetConnectionFactory(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			argsBean.setConnectionFactoryUrl(valConnectionFactoryUrl);
			String result = argsBean.getConnectionFactoryUrl();
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, result, getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testEquals_True(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			boolean result = argsBean.equals(new ArgsBean());
			assertTrueToLog(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			result = argsBean.equals(argsBean);
			assertTrueToLog(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testEquals_False(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_FALSE.toString());
			logger.debug(getStartTestLog());

			boolean result = argsBean.equals(new SendBean());
			assertFalseToLog(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

			argsBean.setSuccessful(true);
			result = argsBean.equals(new ArgsBean());
			assertFalseToLog(result, getEndTestLog(TEST_AND_KO));
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHashCode_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			int result = argsBean.hashCode();
			int again = argsBean.hashCode();
			int another = new ArgsBean().hashCode();

			assertEqualsToLog(again, result, getEndTestLogKO());
			assertEqualsToLog(another, result, getEndTestLog(TEST_AND_KO));

			swapInfoExpected(Integer.toString(result));
			logger.debug("{}{}", getEndTestLogOK(), another);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testHashCode_KO(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			int current = argsBean.hashCode();

			argsBean.setSuccessful(true);
			int result = argsBean.hashCode();
			assertNotEqualsToLog(current, result, getEndTestLogKO());

			swapInfoExpected("not " + current);
			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testToString(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			String result = argsBean.toString();
			assertNotNullToLog(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), "\n" + result);

			swapInfoExpected(EXP_TRUE.toString());
			assertTrueToLog(result.contains(TO_STRING_EMPTY), getEndTestLog(TEST_AND_KO));

			swapInfoExpected("... \ncontains: " + TO_STRING_EMPTY);
			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), "\n" + result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}