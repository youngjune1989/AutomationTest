package test.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.common.base.Function;
import com.thoughtworks.selenium.Selenium;

public class WebDriverUtils {

	public static WebDriver driver;
	private boolean acceptNextAlert = true;

	public WebDriverUtils(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebDriver getDriver() {
		
		return driver;
	}

	public void pause(int millisecs) {
		try {
			Thread.sleep(millisecs);
		} catch (InterruptedException e) {
		}
	}

	public boolean waitForElementEditable(String locator, int seconds) {
		boolean elementEditable = false;
		for (int second = 0;; second++) {

			// If loop is reached 60 seconds then break the loop.
			if (second >= seconds)
				break;

			// Search for element "link=ajaxLink" and if available then break
			// loop.
			// Create the Selenium implementation
			Selenium selenium = new WebDriverBackedSelenium(driver, null);
			try {
				if (selenium.isEditable(locator)) {
					elementEditable = true;
					break;
				}

			} catch (Exception e) {
			}

			// Pause for 1 second.
			pause(1000);
		}

		return elementEditable;

	}

	public boolean waitForElement(By locator, int seconds) {
		boolean elementExist = false;
		for (int second = 0;; second++) {

			// If loop is reached 60 seconds then break the loop.
			if (second >= seconds)
				break;

			// Search for element "link=ajaxLink" and if available then break
			// loop.
			try {
				if (isElementPresent(locator)) {
					elementExist = true;
					break;
				}

			} catch (Exception e) {
				System.out.println(e);
			}

			// Pause for 1 second.
			pause(1000);
		}

		return elementExist;

	}

	public boolean waitForText(String tmplName, int seconds) {
		boolean textExist = false;
		for (int second = 0;; second++) {

			// If loop is reached 60 seconds then break the loop.
			if (second >= seconds)
				break;

			// Search for element "link=ajaxLink" and if available then break
			// loop.
			try {
				if (isTextPresent(tmplName)) {
					textExist = true;
					break;
				}

			} catch (Exception e) {
			}

			// Pause for 1 second.
			pause(1000);
		}

		return textExist;
	}

	/**
	 * @param mis
	 */
	public void waitForPageToLoad(String mis) {
		boolean isLoaded = false;
		int count = 0;
		do {
			if (count++ > 3)
				break;
			try {
				waitForPageToLoad(mis);
				isLoaded = true;
			} catch (Exception ex) {
				continue;
			}
		} while (!isLoaded);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isTextPresent(String text) {
		try {
			driver.getPageSource().contains(text);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public String getWebText(By by) { 
        try { 
        return driver.findElement(by).getText();
        } catch (NoSuchElementException e) { 
        	e.printStackTrace();
            return "Text not existed!"; 
        } 
    }
	
	public void switchToFrame(By by){
		driver.switchTo().frame(driver.findElement(by));
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
	

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	// 将滚动条滚到适合的位置
	public static void setScroll(WebDriver driver, int height) {
		try {
			String setscroll = "document.documentElement.scrollTop=" + height;

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(setscroll);
		} catch (Exception e) {
			System.out.println("Fail to set the scroll.");
		}
	}

	// 输入字段
	public void input(By locator, String inputString) {
		driver.findElement(locator).click();
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(inputString);
	}

	public void click(By locator) {
		driver.findElement(locator).click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	}

	// 处理模态对话框
	public void switchToNewWindow(WebDriver driver, String firstHandler) {
		/* 获取所有窗口的Handler，并存放在Set集合里 */
		Set<String> handlers = driver.getWindowHandles();

		/* 删掉第一个窗口的Handler */
		if (handlers.remove(firstHandler)) {
			System.out.println("已删掉第一个窗口的Handler");
		}
		/* 把Set集合转换成Iterator */
		Iterator<String> it = handlers.iterator();
		try {
			if (it.hasNext()) {
				driver.switchTo().window(it.next());
			}
		} catch (Exception e) {
			System.out.println("没有可用的窗口Handler");
		}
	}

	protected Function<WebDriver, Boolean> isPageLoaded() {
		return new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
	}

	public void waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(isPageLoaded());

	}

	// private Function<WebDriver, Boolean> haveMoreThanOneOption(final By
	// element) {
	// return new Function<WebDriver, Boolean>() {
	// @Override
	// public Boolean apply(WebDriver driver) {
	// WebElement webElement = driver.findElement(element);
	// if (webElement == null) {
	// return false;
	// } else {
	// int size = webElement.findElements(By.tagName("option")).size();
	// return size >= 1;
	// }
	// }
	// };
	// }

	public void waitForDropDownListLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(isPageLoaded());
	}
	
	/**
	 * screen shot on failure
	 */
	public static void takeScreenShot(ITestResult tr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String dateTime = formatter.format(new Date());
		String fileName = dateTime + "_" + tr.getName();
		String filePath = "E:\\wyj\\workspace\\DCV3Test\\report";
		File screenShotFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		String screenshot = filePath + "\\" + fileName + ".jpg";
		try {
			FileUtils.copyFile(screenShotFile, new File(filePath + "\\"
					+ fileName + ".jpg"));
		} catch (IOException e) {
			Reporter.log("Fail to capture screenshot:" + e.getMessage());
		}

		Reporter.setCurrentTestResult(tr);
		Reporter.log(filePath);
		Reporter.log("\r\n");
		Reporter.log("<img src=\"" + screenshot + "\">");

	}

}
