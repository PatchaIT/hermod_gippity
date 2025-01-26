package it.patcha.hermod.gpt;

import it.patcha.hermod.gpt.common.bean.HermodBean;
import it.patcha.hermod.gpt.common.bean.ui.input.ArgsBean;
import it.patcha.hermod.gpt.common.constant.HermodConstants;
import it.patcha.hermod.gpt.common.util.HermodUtils;
import it.patcha.hermod.gpt.config.SpringConfig;
import it.patcha.hermod.gpt.ui.input.dispatch.common.error.JobDispatcherException;
import it.patcha.hermod.gpt.ui.input.dispatch.main.MainJobDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static it.patcha.hermod.gpt.common.error.codes.ErrorType.RE99;

/** Main class of the application, catches call from command line. */
@Component
public class HermodGippity {
	private static final Logger logger = LoggerFactory.getLogger(HermodGippity.class);
	/* TODO:
	 *  5. Write unit tests for all classes (Beans and Types (Enum) included)
	 *  6. Check SonarCloud and solve all its issues
	 *  7. Write documentation
	 *  8. GitHub Workflow to zip .jar and publish as artifact into release artifact
	 */

	/** The JobDispatcher will delegate the work to other classes by case. */
	@Autowired
	private MainJobDispatcher mainJobDispatcher;

	public HermodGippity(MainJobDispatcher mainJobDispatcher) {
	}

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

		} catch (JobDispatcherException e) {
			logger.error(e.getMessage(), e);
			return -1;

		} catch (Exception e) {
			logger.error("{} : {} : {}", RE99.getCode(), RE99.getMessage(), e.getMessage(), e);
			return -1;
		}

		if (hermodBean != null && hermodBean.isSuccessful()) {
			logger.info("Done and done, with args: {}", argsBean);
			return 0;
		} else {
			logger.info("Something went wrong, please check logs, with args: {}", argsBean);
			return 1;
		}

	}

}
