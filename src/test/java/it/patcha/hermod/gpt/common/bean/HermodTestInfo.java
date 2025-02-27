package it.patcha.hermod.gpt.common.bean;

import it.patcha.hermod.gpt.common.constant.HermodConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.TestInfo;

import java.io.Serial;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import static it.patcha.hermod.gpt.common.HermodBaseTest.LOG_DEF_EXPECTED;
import static it.patcha.hermod.gpt.common.HermodBaseTest.LOG_DEF_OUTCOME;
import static it.patcha.hermod.gpt.common.HermodBaseTest.LOG_DEF_TITLE;
import static it.patcha.hermod.gpt.common.HermodBaseTest.LOG_TEST_END;

/**
 * This implementation has the purpose to enrich
 *   {@link TestInfo} with further information and
 *   formatted text for logs.
 * <p>
 * To get an instance of {@link TestInfo}, just add the parameter
 *   {@code TestInfo testInfo} to your test method.
 * </p>
 * I.e.: {@code @Test void testMethod(TestInfo testInfo) {}}
 *
 * @see TestInfo
 */
@Getter
@Setter
public class HermodTestInfo extends HermodBean implements TestInfo {

	@Serial
	private static final long serialVersionUID = -5821959305164624866L;
	private static final int ID_ODD_NUM = 0;

	private TestInfo testInfo;
	private String outcome;

	/**
	 * This implementation has the purpose to enrich
	 *   {@link TestInfo} with further information and
	 *   formatted text for logs.
	 * <p>
	 * To get an instance of {@link TestInfo}, just add the parameter
	 *   {@code TestInfo testInfo} to your test method.
	 * </p>
	 * I.e.: {@code @Test void testMethod(TestInfo testInfo) {}}
	 *
	 * @param testInfo the instance of {@link TestInfo} given by jUnit
	 * @param expected the expected result from the test:
	 *   this is just the starting value, you'll be able to update it
	 *   later, if needed, with the use of {@link #swapInfoExpected(String)}
	 * @see TestInfo
	 */
	public HermodTestInfo(TestInfo testInfo, String expected) {
		this.testInfo = testInfo;
		this.outcome = getInfoOutcome(expected);
	}

	@Override
	public String getDisplayName() {
		return testInfo != null ? testInfo.getDisplayName() : LOG_DEF_TITLE;
	}

	@Override
	public Set<String> getTags() {
		return testInfo != null ? testInfo.getTags() : Set.of();
	}

	@Override
	public Optional<Class<?>> getTestClass() {
		return testInfo != null ? testInfo.getTestClass() : Optional.empty();
	}

	@Override
	public Optional<Method> getTestMethod() {
		return testInfo != null ? testInfo.getTestMethod() : Optional.empty();
	}

	/**
	 * Swaps the {@code expected} part into {@link HermodTestInfo#getOutcome()}.
	 *
	 * @param expected the new expected result for current test
	 * @see HermodTestInfo
	 */
	public void swapInfoExpected(String expected) {
		outcome = getInfoOutcome(expected);
	}

	/**
	 * Generate a log text for a test outcome, based on
	 *   test method's name ({@code title}) and
	 *   {@code expected} result.
	 *
	 * @param expected the expected result from the test
	 * @return a log text for a test outcome, based on
	 * 	 test method's name ({@code title}) and
	 * 	 {@code expected} result
	 */
	private String getInfoOutcome(String expected) {
		expected = !StringUtils.isEmpty(expected) ? expected : "";

		String title = getDisplayName();
		return !StringUtils.isEmpty(title) ?
				String.format(LOG_TEST_END, title, expected) :
				String.format(LOG_DEF_OUTCOME,
						StringUtils.isEmpty(expected) ? "" :
								String.format(LOG_DEF_EXPECTED, expected));
	}

	@Override
	public boolean equals (Object obj) {
		if(!(obj instanceof HermodTestInfo that))
			return false;

		if (that == this)
			return true;

		return new EqualsBuilder()
				.append(this.testInfo, that.getTestInfo())
				.append(this.outcome, that.getOutcome())

				.append(this.isSuccessful(), that.isSuccessful())

				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(
				HermodConstants.getInitialNonZeroOddNumbers()[ID_ODD_NUM],
				HermodConstants.getMultiplierNonZeroOddNumbers()[ID_ODD_NUM]
		)
				.append(this.testInfo)
				.append(this.outcome)

				.append(this.isSuccessful())

				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("testInfo", this.testInfo)
				.append("outcome", this.outcome)

				.append("successful", this.isSuccessful())

				.toString();
	}

}
