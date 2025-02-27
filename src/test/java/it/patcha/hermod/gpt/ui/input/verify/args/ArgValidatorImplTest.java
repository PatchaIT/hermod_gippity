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
import static it.patcha.hermod.gpt.common.constant.HermodConstants.INVALID_DATA_TYPE;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR03;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR04;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.IR99;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI01;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.UI02;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ArgValidatorImplTest extends HermodBaseTest {

	@InjectMocks
	private ArgValidatorImpl validator;

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

		argsBean = new ArgsBean(args);
	}

	@Test
	void testCastToArgsBean_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = validator.castToArgsBean(argsBean);
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
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.castToArgsBean(new SendBean()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			ArgsBean result = validator.validateArgs(argsBean);
			assertNotNullToLog(result, getEndTestLogKO());

			swapInfoExpected(valConnectionFactoryUrl);
			assertEqualsToLog(valConnectionFactoryUrl, result.getConnectionFactoryUrl(), getEndTestLogKO());
			swapInfoExpected(valRequestQueueName);
			assertEqualsToLog(valRequestQueueName, result.getRequestQueueName(), getEndTestLogKO());
			swapInfoExpected(valJmsMessageText);
			assertEqualsToLog(valJmsMessageText, result.getMessageText(), getEndTestLogKO());
			swapInfoExpected(objJmsMessageFile.getPath());
			assertEqualsToLog(objJmsMessageFile, result.getMessageFile(), getEndTestLogKO());
			swapInfoExpected(EXP_TRUE.toString());
			assertTrueToLog(result.isSuccessful(), getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionUI01_ConnectionFactoryUrl(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{
					argRequestQueueName, valRequestQueueName,
					argJmsMessageText, valJmsMessageText,
					argJmsMessageFilePath, valJmsMessageFilePath
			});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());

			swapInfoExpected(UI01.getCode());
			assertEqualsToLog(UI01.getCode(), exception.getCode(), getEndTestLogKO());
			swapInfoExpected(EXP_CONTAINS + UI01.getMessage());
			assertTrueToLog(exception.getMessage().contains(UI01.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionUI01_RequestQueueName(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{
					argConnectionFactoryUrl, valConnectionFactoryUrl,
					argJmsMessageText, valJmsMessageText,
					argJmsMessageFilePath, valJmsMessageFilePath
			});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());

			swapInfoExpected(UI01.getCode());
			assertEqualsToLog(UI01.getCode(), exception.getCode(), getEndTestLogKO());
			swapInfoExpected(EXP_CONTAINS + UI01.getMessage());
			assertTrueToLog(exception.getMessage().contains(UI01.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionUI01_MessageText(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{
					argConnectionFactoryUrl, valConnectionFactoryUrl,
					argRequestQueueName, valRequestQueueName
			});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());

			swapInfoExpected(UI01.getCode());
			assertEqualsToLog(UI01.getCode(), exception.getCode(), getEndTestLogKO());
			swapInfoExpected(EXP_CONTAINS + UI01.getMessage());
			assertTrueToLog(exception.getMessage().contains(UI01.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionUI02(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{argGui});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());

			swapInfoExpected(UI02.getCode());
			assertEqualsToLog(UI02.getCode(), exception.getCode(), getEndTestLogKO());
			swapInfoExpected(EXP_CONTAINS + UI02.getMessage());
			assertTrueToLog(exception.getMessage().contains(UI02.getMessage()), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionIR03(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{argReplyQueueName});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());
			swapInfoExpected(IR03.getCode());
			assertEqualsToLog(IR03.getCode(), exception.getCode(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionIR04(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{argJmsMessageText});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());
			swapInfoExpected(IR04.getCode());
			assertEqualsToLog(IR04.getCode(), exception.getCode(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	@Test
	void testValidateArgs_ExceptionIR99(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + ValidatorException.class.getName());
			logger.debug(getStartTestLog());

			argsBean = new ArgsBean(args = new String[]{INVALID_DATA_TYPE});
			ValidatorException exception =
					assertThrowsToLog(ValidatorException.class, () -> validator.validateArgs(argsBean), getEndTestLogKO());
			swapInfoExpected(IR99.getCode());
			assertEqualsToLog(IR99.getCode(), exception.getCode(), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}