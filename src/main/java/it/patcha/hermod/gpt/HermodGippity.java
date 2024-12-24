package it.patcha.hermod.gpt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HermodGippity {
	private static final Logger logger = LoggerFactory.getLogger(HermodGippity.class);

	private HermodGippity() {}

	public static void main(String[] args) {
		if (args.length > 0) {
			logger.info("This is a stub, your argument is: {}", args[0]);
		} else {
			logger.info("This is a stub, you put no argument");
		}
	}
}
