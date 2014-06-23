package test.tasks;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import test.utilities.PropUtils;
import test.utilities.WebDriverUtils;

public class CommonTasks {
	private final static String PROP_NAME = "test/appObjects/commonPages.properties";

	private WebDriverUtils utils;

	private WebDriver driver;

	private Properties elemMap;

	public CommonTasks(WebDriverUtils utils) {
		this.utils = utils;
		driver = this.utils.getDriver();
		elemMap = PropUtils.getProperties(PROP_NAME);
	}

	public WebDriverUtils getUtils() {
		return utils;
	}

	public void openSite(String baseUrl) {
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void login(HashMap<String, Object> paraMap) {
		System.out.println("the elemMap is" + elemMap + "..............");
		utils.input(
				By.xpath((String) elemMap.get(CommonConstants.LOGIN_USERNAME)),
				(String) paraMap.get(CommonConstants.LOGIN_USERNAME));
		utils.input(
				By.xpath((String) elemMap.get(CommonConstants.LOGIN_PASSWORD)),
				(String) paraMap.get(CommonConstants.LOGIN_PASSWORD));
		utils.click(By.xpath((String) elemMap.get(CommonConstants.LOGIN_SUBMIT)));
		utils.pause(5000);
	}

	public void logout() {
		utils.click(By.xpath((String)elemMap.getProperty(CommonConstants.LOGINOUT)));
	}

	public void filpPage() {
		utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE20)));
		String select20 = utils
				.getDriver()
				.findElement(
						By.xpath((String) elemMap.get(CommonConstants.PAGE20)
								+ "/p")).getAttribute("class");
//		Assert.assertEquals(select20, "select");
		utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE50)));
		String select50 = utils
				.getDriver()
				.findElement(
						By.xpath((String) elemMap.get(CommonConstants.PAGE50)
								+ "/p")).getAttribute("class");
//		Assert.assertEquals(select50, "select");
		utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE10)));
		String select10 = utils
				.getDriver()
				.findElement(
						By.xpath((String) elemMap.get(CommonConstants.PAGE10)
								+ "/p")).getAttribute("class");
//		Assert.assertEquals(select10, "select");
		int pageNum = Integer
				.parseInt(utils.getWebText(By.xpath((String) elemMap.get(CommonConstants.PAGENUM))));// 获取页数
		if (pageNum > 1) {
			utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE_NEXT)));
			utils.pause(2000);
			utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE_PREV)));
			utils.pause(2000);
			utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE_LAST)));
			utils.pause(2000);
			utils.click(By.xpath((String) elemMap.get(CommonConstants.PAGE_FIRST)));
		}
	}

}
