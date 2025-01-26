package it.patcha.hermod.gpt.ui.input.verify.args;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.core.logic.SendBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;
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
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR03;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR04;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI01;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ArgValidatorImplTest extends HermodBaseTest {

	private static final String ILLEGAL_ARGUMENT = "illegal arguments";

	@InjectMocks
	private ArgValidatorImpl validator;

	private String[] args = null;
	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		args = new String[]{
				ARG_CONNECTION_FACTORY_URL, CONNECTION_FACTORY_URL,
				ARG_REQUEST_QUEUE_NAME, REQUEST_QUEUE_NAME,
				ARG_JMS_MESSAGE_TEXT, JMS_MESSAGE_TEXT,
				ARG_JMS_MESSAGE_FILE_PATH, JMS_MESSAGE_FILE_PATH
		};

		argsBean = new ArgsBean(args);
	}

	@Test
	void testCastToArgsBean(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = validator.castToArgsBean(argsBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			ValidatorException exception =
					assertThrows(ValidatorException.class, () -> validator.castToArgsBean(new SendBean()), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = validator.validateArgs(argsBean);
			assertNotNull(result, getEndTestLogKO());

			swapInfoExpected(CONNECTION_FACTORY_URL);
			assertEquals(CONNECTION_FACTORY_URL, result.getConnectionFactoryUrl(), getEndTestLogKO());
			swapInfoExpected(REQUEST_QUEUE_NAME);
			assertEquals(REQUEST_QUEUE_NAME, result.getRequestQueueName(), getEndTestLogKO());
			swapInfoExpected(JMS_MESSAGE_TEXT);
			assertEquals(JMS_MESSAGE_TEXT, result.getMessageText(), getEndTestLogKO());
			swapInfoExpected(JMS_MESSAGE_FILE.getPath());
			assertEquals(JMS_MESSAGE_FILE, result.getMessageFile(), getEndTestLogKO());
			swapInfoExpected(EXP_TRUE.toString());
			assertTrue(result.isSuccessful(), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			argsBean = new ArgsBean(args = new String[]{});
			ValidatorException exception =
					assertThrows(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(UI01.getCode());
			assertEquals(UI01.getCode(), exception.getCode(), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(EXP_CONTAINS + UI01.getMessage());
			assertTrue(exception.getMessage().contains(UI01.getMessage()), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			argsBean = new ArgsBean(args = new String[]{ILLEGAL_ARGUMENT});
			exception =
					assertThrows(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(IR03.getCode());
			assertEquals(IR03.getCode(), exception.getCode(), getEndTestLog(TEST_AND_KO));

			swapInfoExpected(EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			argsBean = new ArgsBean(args = new String[]{ARG_JMS_MESSAGE_TEXT});
			exception =
					assertThrows(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLog(TEST_AND_KO));
			swapInfoExpected(IR04.getCode());
			assertEquals(IR04.getCode(), exception.getCode(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}{}", getEndTestLog(TEST_AND_OK), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}