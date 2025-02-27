package it.patcha.hermod.gpt.ui.input.read.args;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.read.common.error.InfoReaderException;
import it.patcha.hermod.gpt.ui.input.verify.args.ArgValidator;
import it.patcha.hermod.gpt.ui.input.verify.common.error.ValidatorException;
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
class ArgInfoReaderImplTest extends HermodBaseTest {

	private ArgInfoReaderImpl infoReader;

	@Mock
	private ArgValidator argValidator;

	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();

		infoReader = new ArgInfoReaderImpl(argValidator);
	}

	@Test
	void testHandleOptions_OK(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(argsBean).when(argValidator).castToArgsBean(argsBean);
			doReturn(argsBean).when(argValidator).validateArgs(argsBean);

			HermodBean result = infoReader.handleOptions(argsBean);
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
	void testHandleOptions_Exception(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_EXCEPTION + LOG_NL + InfoReaderException.class.getName());
			logger.debug(getStartTestLog());

			doThrow(new ValidatorException(errorCause)).when(argValidator).castToArgsBean(argsBean);

			InfoReaderException exception =
					assertThrowsToLog(InfoReaderException.class, () -> infoReader.handleOptions(argsBean), getEndTestLogKO());

			logger.debug("{}{}{}", getEndTestLogOK(), LOG_NL, exception.toString());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}