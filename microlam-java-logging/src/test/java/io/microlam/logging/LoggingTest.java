package io.microlam.logging;

import java.util.Arrays;
import java.util.logging.LogManager;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTest {

	static {
		LogManager logManager = LogManager.getLogManager();
		logManager.reset();
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%2$s %4$s: %5$s%6$s");
	}
	
	Logger logger = LoggerFactory.getLogger(LoggingTest.class);
	
	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(LoggingTest.class.getName());
	
	@Test
	public void testLogJson() {
		LambdaHandler lambdaHandler = new LambdaHandler();
		LOGGER.addHandler(lambdaHandler);
		System.out.println(Arrays.asList(LOGGER.getHandlers()));
		logger.atInfo().log("this is a test {}", "test1", new RuntimeException("this is a test"));
//		LOGGER.log(Level.SEVERE, new RuntimeException("this is a test"), () -> "this is a test " + "test1");
	}
}
