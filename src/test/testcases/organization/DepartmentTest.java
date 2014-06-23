package test.testcases.organization;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test.tasks.CommonTasks;
import test.tasks.organization.TestDepartmentTasks;
import test.tasks.organization.TestOrganizationTasks;
import test.utilities.WebDriverUtils;
import test.utilities.XMLParser;

public class DepartmentTest {

	private HashMap<String, Object> paraMap;

	private TestOrganizationTasks toTasks;
	
	private TestDepartmentTasks tdTasks;

	private WebDriverUtils utils;
	
	private CommonTasks coTasks;

	public WebDriver driver;
	
	private static Logger logger = Logger.getLogger(DepartmentTest.class);

	@Parameters({"organization_para", "baseUrl"})
	@BeforeClass
	public void setup(String paraFile,String baseUrl) {
		System.setProperty("webdriver.ie.driver",
				".\\utils\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(capabilities);
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);
		utils = new WebDriverUtils(driver);
		coTasks = new CommonTasks(utils);
		toTasks = new TestOrganizationTasks(utils);
		tdTasks = new TestDepartmentTasks(utils);
		
		coTasks.openSite(baseUrl + "/dc_base");
		coTasks.login(paraMap);
	}
	
	@BeforeMethod
	public void beforeMethod(){
		driver.navigate().refresh();
		toTasks.clickModule();
		tdTasks.intoDepartment();
		tdTasks.maxmizeWindow();
	}
	
	@Parameters({ "department_para"})
	@Test
	public void testAdd(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);
		logger.info("the paraMap is" + paraMap);
		
		tdTasks.clickAddButton();
		tdTasks.chooseParent();
		tdTasks.inputName(paraMap);
		tdTasks.chooseKind();
		tdTasks.choosePosition();
		tdTasks.searchPosition(paraMap);
		tdTasks.clickSaveButton();
	}
	
	@Parameters({ "department_para"})
	@Test
	public void testEdit(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);
		logger.info("the paraMap is" + paraMap);
		
		tdTasks.editTarget();
		
		if (!tdTasks.isEnable()) {
			tdTasks.enable();
		}
		tdTasks.inputName(paraMap);
		tdTasks.chooseKind();
		tdTasks.choosePosition();
		tdTasks.searchPosition(paraMap);
		tdTasks.clickSaveButton();
		
	}
	
	@Test
	public void testDelete() {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		tdTasks.editTarget();
		if (!tdTasks.isEnable()) {
			tdTasks.enable();
		}
		tdTasks.delete();
		
	}
	
	@Test
	public void testEnable() {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		
		tdTasks.editTarget();
		if (tdTasks.isEnable()) {
			tdTasks.disable();
		}
		
		if (!tdTasks.isEnable()) {
			tdTasks.enable();
		}
		
		
	}

	@AfterClass
	public void quit() {
		if (driver != null) {
			driver.quit();
		}
	}
}
