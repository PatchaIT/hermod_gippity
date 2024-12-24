package it.patcha.hermod.gpt;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HermodGippityTest {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(HermodGippity.class);
	private ListAppender<ILoggingEvent> listAppender;

	@BeforeEach
	void setUp() {
		listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
	}

	@Test
	void testMainWithoutArgument() {
		HermodGippity.main(new String[]{});
		ILoggingEvent loggingEvent = listAppender.list.get(0);
		String logMessage = loggingEvent.getFormattedMessage();
		assertEquals("This is a stub, you put no argument", logMessage);
	}

	@Test
	void testMainWithArgument() {
		String argument = "argument";
		HermodGippity.main(new String[]{argument});
		ILoggingEvent loggingEvent = listAppender.list.get(0);
		String logMessage = loggingEvent.getFormattedMessage();
		assertEquals("This is a stub, your argument is: " + argument, logMessage);
	}
}
