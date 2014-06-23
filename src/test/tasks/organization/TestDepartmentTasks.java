package test.tasks.organization;

import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.seleniumemulation.GetText;

import test.utilities.PropUtils;
import test.utilities.WebDriverUtils;

public class TestDepartmentTasks {
	private final static String PROP_NAME = "test/appObjects/organization/departmentPages.properties";

	private WebDriverUtils utils;

	private WebDriver driver;

	private Properties elemMap;

	public TestDepartmentTasks(WebDriverUtils utils) {
		this.utils = utils;
		// driver = this.utils.getDriver();
		elemMap = PropUtils.getProperties(PROP_NAME);
	}

	public WebDriverUtils getUtils() {
		return utils;
	}

	public void maxmizeWindow() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.MAXIMIZE_WINDOW)));
		utils.pause(2000);
	}

	public void typeTxtField(HashMap<String, Object> paraMap) {
		System.out.println("the elemMap is" + elemMap + "..............");
		utils.input(By.xpath((String) elemMap
				.get(TestOrganizationConstants.LOGIN_USERNAME)),
				(String) paraMap.get(TestOrganizationConstants.LOGIN_USERNAME));
		utils.input(By.xpath((String) elemMap
				.get(TestOrganizationConstants.LOGIN_PASSWORD)),
				(String) paraMap.get(TestOrganizationConstants.LOGIN_PASSWORD));
	}

	public void intoDepartment() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.DEPARTMENT)));
	}

	public void clickAddButton() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.ADD_BUTTON)));
	}

	public void chooseParent() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.PARENT_TREE)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.PARENT)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.OK_BUTTON_01)));
		utils.pause(2000);
	}

	public void inputName(HashMap<String, Object> paraMap) {
		utils.input(By.xpath((String) elemMap
				.get(TestDepartmentConstants.DEPARTMENT_NAME)),
				(String) paraMap.get(TestDepartmentConstants.DEPARTMENT_NAME));

	}

	public void chooseKind() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.DEPARTMENT_KIND)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.CHOOSE_GRADE_AND_COURSE)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.COURSE)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.CONTROL_DOWN)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.GRADE)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.OK_BUTTON_02)));
		utils.pause(2000);
	}

	public void choosePosition() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.FROM_01)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.FROM_02)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.RIGHT)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.TO_01)));
		utils.click(By.xpath((String) elemMap.get(TestDepartmentConstants.LEFT)));
	}

	public void searchPosition(HashMap<String, Object> paraMap) {
		utils.input(By.xpath((String) elemMap
				.get(TestDepartmentConstants.SEARCH_POSITION)),
				(String) paraMap.get(TestDepartmentConstants.SEARCH_POSITION));

	}

	public void clickSaveButton() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.SAVE_BUTTON)));

	}

	public void editTarget() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.EDIT_POSITION_PARENT)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.EDIT_POSITION)));

	}

	public void delete() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.DELETE_BUTTON)));
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.CANCEL_BUTTON_02)));
	}

	public void enable() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.ENABLE_BUTTON)));
	}
	
	public void disable() {
		utils.click(By.xpath((String) elemMap
				.get(TestDepartmentConstants.DISABLE_BUTTON)));
	}

	public String getStatus(){
		String status = utils.getWebText((By.xpath((String) elemMap
				.get(TestDepartmentConstants.POSITION_STATUS))));
		return status;
	}
	public boolean isEnable() {
		boolean flag = false;
		
		if (getStatus().equals("启用")) {
			flag = true;
		}

		if (getStatus().equals("禁用")) {
			flag = false;
		}
		return flag;
	}

	
}
