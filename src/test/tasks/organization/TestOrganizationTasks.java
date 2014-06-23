package test.tasks.organization;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import test.utilities.*;

public class TestOrganizationTasks {

	private final static String PROP_NAME = "test/appObjects/organizationPages.properties";

	private WebDriverUtils utils;

	private WebDriver driver;

	private Properties elemMap;
	

	public TestOrganizationTasks(WebDriverUtils utils) {
		this.utils = utils;
//		driver = this.utils.getDriver();
		elemMap = PropUtils.getProperties(PROP_NAME);
	}
	
	public WebDriverUtils getUtils(){
		return utils;
	}

	public void typeTxtField(HashMap<String, Object> paraMap) {
		System.out.println("the elemMap is" + elemMap + "..............");
		utils.input(
				By.xpath((String) elemMap.get(TestOrganizationConstants.LOGIN_USERNAME)),
				(String) paraMap.get(TestOrganizationConstants.LOGIN_USERNAME));
		utils.input(
				By.xpath((String) elemMap.get(TestOrganizationConstants.LOGIN_PASSWORD)),
				(String) paraMap.get(TestOrganizationConstants.LOGIN_PASSWORD));
	}

	public void clickModule(){
		utils.click(By.xpath((String) elemMap
				.get(TestOrganizationConstants.MODULE)));
	}
	
	

//	public void verifyResult(HashMap<String, Object> paraMap) {
//		verifyTrue(utils.isTextPresent((String) paraMap
//				.get(TestOrganizationConstants.VERIFY_STRING)));
//	}
}