package test.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import test.testcases.organization.DepartmentTest;



public class LogEventListener  extends TestListenerAdapter implements WebDriverEventListener {

	private Log log = LogFactory.getLog(this.getClass());
	private String originalValue;
	private By lastFindBy;
	private int m_count = 0;
	private static Logger logger = Logger.getLogger(LogEventListener.class);
	public static final String config = ".//conf//config.properties";

	
	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		 log.info("WebDriver changing value in element found "+lastFindBy+" from '"+originalValue+"' to '"+element.getAttribute("value")+"'");
		
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		String locator = element.toString().split("-> ")[1];
		log.info("Webdriver clicking on : '" + locator.substring(0,locator.length()-1) + "'");
	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateTo(String str, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String str, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		originalValue = element.getAttribute("value");
		
	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		lastFindBy  = by;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		log.info("Webdriver navigating to : '" + url + "'");
		
	}

	@Override
	public void beforeScript(String str, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onException(Throwable error, WebDriver driver) {
		if (error.getClass().equals(NoSuchElementException.class)) {
			log.error("Webdriver error : Element not found " + lastFindBy);
		} else {
			log.error("Webdriver error : ",error);
		}
		
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		logger.info(tr.getName() + "--Test method failed\n");
		takeScreenShot(tr);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		logger.info(tr.getName() + "--Test method skipped\n");
		takeScreenShot(tr);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info(tr.getName() + "--Test method success\n");
	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info(tr.getName() + " Start");
	}

	/**
	 * ç›®çš„ï¼šè§£å†³index.htmlæ–‡ä»¶ä¸­ç”¨ä¾‹ä¸ªæ•°ä¸�å¯¹çš„é—®é¢˜ 
	 * æ–¹æ³•ï¼š ç­‰æ‰€æœ‰ç”¨ä¾‹è¿�è¡Œå®Œä¹‹ å�Žï¼Œæ£€æŸ¥ç”¨ä¾‹ï¼Œ
	 * æŒ‰ç…§class+method+dataprodiverçš„å��ç§°ç”Ÿæˆ�hashcodeèŽ·å�–å”¯ä¸€idï¼Œ 
	 * å¦‚æžœfailçš„ç”¨ä¾‹ä¸­å­˜åœ¨é‡�å¤�çš„åˆ™åœ¨ failçš„ç”¨ä¾‹ä¸­å‰”é™¤æŽ‰
	 */
	@Override
		public void onFinish(ITestContext testContext) {
			super.onFinish(testContext);
			
			//List of test results which we will removed later
			ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
			//Collect all ids from passed tests
			Set<Integer> passedTestIds = new HashSet<Integer>();
			for (ITestResult passedTest : testContext.getPassedTests().getAllResults()){
				logger.info("PassedTests = " + passedTest.getName());
			}
			
			Set<Integer> failedTestIds = new HashSet<Integer>();
			for(ITestResult failedTest : testContext.getFailedTests().getAllResults()){
				logger.info("FailedTests = " + failedTest.getName());
				//id = class+method+dataprovider
				int failedTestId = getId(failedTest);
				if(failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)){
					testsToBeRemoved.add(failedTest);
					logger.info(failedTest);
				}else{
					failedTestIds.add(failedTestId);
					logger.info(failedTestId);
				}
			}
			
			//finally delete all tests that are marked
			for(Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator();iterator.hasNext();){
				logger.info("remove");
				ITestResult testResult = iterator.next();
				if(testsToBeRemoved.contains(testResult)){
					logger.info("Removed repeat Fail Test : " + testResult.getName());
					iterator.remove();
				}
			}
			
			
		}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode( result.getParameters()) : 0);
		return id;
	}

	// private void log(String string) {
	// System.out.print(string);
	// if (++m_count % 40 == 0) {
	// System.out.println("");
	// }
	// }

	/**
	 * screen shot on failure
	 */
	private void takeScreenShot(ITestResult tr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dateTime = formatter.format(new Date());
		String fileName = dateTime + "_" + tr.getName();
		String filePath = "E:\\wyj\\workspace\\dcv3\\log";
		File screenShotFile = ((TakesScreenshot) WebDriverUtils.driver)
				.getScreenshotAs(OutputType.FILE);
		String screenshot = filePath + "\\" + fileName + ".jpg";
		try {
			FileUtils.copyFile(screenShotFile, new File(filePath + "\\"
					+ fileName + ".jpg"));
		} catch (IOException e) {
			System.out.println("Fail to capture screenshot:" + e.getMessage());
		}

		Reporter.setCurrentTestResult(tr);
		Reporter.log(filePath);
		Reporter.log("<a><img src=" + screenshot + "/></a>");

	}

}
