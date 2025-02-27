package it.patcha.hermod.gpt;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static it.patcha.hermod.gpt.common.constant.HermodConstants.OUTCOME_ERROR;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.OUTCOME_NEGATIVE;
import static it.patcha.hermod.gpt.common.constant.HermodConstants.OUTCOME_POSITIVE;
import static it.patcha.hermod.gpt.common.error.codes.ErrorType.RE99;

/** Main class of the application, catches call from command line. */
@Component
@RequiredArgsConstructor
public class HermodGippity {
	private static final Logger logger = LoggerFactory.getLogger(HermodGippity.class);

	/** The JobDispatcher will delegate the work to other classes by case. */
	private final MainJobDispatcher mainJobDispatcher;

	/**
	 * Catches the call from command line.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		ApplicationContext context = HermodUtils.getContext(SpringConfig.class);
		HermodGippity hermodGippity = context.getBean(HermodGippity.class);
		int result = hermodGippity.run(args);

		if (result == 0)
			logger.info(HermodConstants.MAIN_POSITIVE_LOG);
		else
			logger.info(HermodConstants.MAIN_NEGATIVE_LOG);
	}

	/**
	 * Delegate to JobDispatcher and gets the result.
	 *
	 * @param args The command line arguments.
	 */
	public int run(String[] args) {
		ArgsBean argsBean = new ArgsBean(args);
		HermodBean hermodBean = null;

		try {
			hermodBean = mainJobDispatcher.handleJobs(argsBean);

			if (hermodBean != null && hermodBean.isSuccessful()) {
				logger.info(OUTCOME_POSITIVE, argsBean);
				return 0;
			} else {
				logger.info(OUTCOME_NEGATIVE, argsBean);
				return 1;
			}

		} catch (JobDispatcherException e) {
			logger.error(e.getMessage(), e);
			return -1;

		} catch (Exception e) {
			logger.error(OUTCOME_ERROR, RE99.getCode(), RE99.getMessage(), e.getMessage(), e);
			return -1;
		}

	}

}
