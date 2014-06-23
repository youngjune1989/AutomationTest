package test.utilities;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestngRetry implements IRetryAnalyzer {

	public static Logger logger = Logger.getLogger(TestngRetry.class);
	private int retryCount = 1;
	private static int maxRetryCount;
	private static ConfigHelper config;

	static {
		// å¤–å›´æ–‡ä»¶é…�ç½®æœ€å¤§è¿�è¡Œæ¬¡æ•°
		config = new ConfigHelper(CustomListener.config);
		maxRetryCount = config.getMaxRunCount();
		logger.info("maxRunCount " + maxRetryCount);
	}

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "running retry for '" + result.getName()
					+ "' on class" + this.getClass().getName() + "Retrying "
					+ retryCount + " times";
			logger.info(message);
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount = " + (retryCount + 1));
			retryCount++;
			return true;
		}

		return false;
	}

}
