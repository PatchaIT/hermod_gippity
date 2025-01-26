package it.patcha.hermod.gpt.ui.input.read.args;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.verify.args.ArgValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_FALSE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_NOT_NULL;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_KO;
import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.TEST_AND_OK;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ArgInfoReaderImplTest extends HermodBaseTest {

	@InjectMocks
	private ArgInfoReaderImpl infoReader;

	@Mock
	private ArgValidator argValidator;

	private ArgsBean argsBean;

	@BeforeEach
	void setUp() {
		argsBean = new ArgsBean();
	}

	@Test
	void testHandleOptions(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_NOT_NULL.toString());
			logger.debug(getStartTestLog());

			doReturn(argsBean).when(argValidator).castToArgsBean(argsBean);
			doReturn(argsBean).when(argValidator).validateArgs(argsBean);

			HermodBean result = infoReader.handleOptions(argsBean);
			assertNotNull(result, getEndTestLogKO());

			logger.debug("{}{}", getEndTestLogOK(), result);

			swapInfoExpected(EXP_FALSE.toString());
			assertFalse(result.isSuccessful(), getEndTestLog(TEST_AND_KO));

			logger.debug("{}{}", getEndTestLog(TEST_AND_OK), result.isSuccessful());

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

}