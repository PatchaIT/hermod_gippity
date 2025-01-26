package it.patcha.hermod.gpt.common.bean;

import it.patcha.hermod.gpt.common.HermodBaseTest;
import it.patcha.hermod.gpt.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.Serial;

import static it.patcha.hermod.gpt.common.HermodBaseTest.TestOutcome.EXP_TRUE;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class HermodBeanTest extends HermodBaseTest {

	@InjectMocks
	private HermodBeanImpl hermodBean;

	@Test
	void testSetIsSuccessful(TestInfo testInfo) throws Exception {
		try {
			enrichTestInfo(testInfo, EXP_TRUE.toString());
			logger.debug(getStartTestLog());

			hermodBean.setSuccessful(true);
			boolean result = hermodBean.isSuccessful();

			assertTrue(result, getEndTestLogKO());
			logger.debug("{}{}", getEndTestLogOK(), result);

		} catch (Exception e) {
			logger.error("{}{}", getEndTestLogKO(), e.getClass().getSimpleName(), e);
			throw e;
		}
	}

	/** An extension of HermodBean used only for test purposes */
	private static class HermodBeanImpl extends HermodBean {
		@Serial private static final long serialVersionUID = 5922565261036404513L;
		@Override public boolean equals(Object obj) {return false;}
		@Override public int hashCode() {return 0;}
		@Override public String toString() {return "";}
	}

}