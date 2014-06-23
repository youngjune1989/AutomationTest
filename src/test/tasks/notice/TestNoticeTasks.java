package test.tasks.notice;

import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import test.utilities.PropUtils;
import test.utilities.WebDriverUtils;

public class TestNoticeTasks {
	private final static String PROP_NAME = "test/appObjects/notice/noticePages.properties";

	private WebDriverUtils utils;

	// private WebDriver driver;

	private Properties elemMap;

	public TestNoticeTasks(WebDriverUtils utils) {
		this.utils = utils;
		// driver = this.utils.getDriver();
		elemMap = PropUtils.getProperties(PROP_NAME);
	}

	public WebDriverUtils getUtils() {
		return utils;
	}

	public void selectSchool() {
		new Select(utils.getDriver()
				.findElement(
						By.xpath((String) elemMap
								.get(TestNoticeConstants.LOGIN_SCHOOL))))
				.selectByIndex(1);
	}

	public String getUserNamePath() {
		return (String) elemMap.get(TestNoticeConstants.LOGIN_USERNAME);
	}

	public String getPsdPath() {
		return (String) elemMap.get(TestNoticeConstants.LOGIN_PASSWORD);
	}

	public String getSubmitPath() {
		return (String) elemMap.get(TestNoticeConstants.LOGIN_SUBMIT);
	}

	public void typeTxtField(HashMap<String, Object> paraMap) {
		System.out.println("the elemMap is" + elemMap + "..............");
		//如果为空则添加，否则编辑
		String titleTxt = utils.getDriver().findElement(By.xpath((String) elemMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD))).getAttribute("value");
		System.out.println("title"+titleTxt);
		if (titleTxt.length()==0) {
			utils.input(By.xpath((String) elemMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD)), (String) paraMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD));
			utils.click(By.xpath((String) elemMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD)));
			Actions actions = new Actions(utils.getDriver());
			actions.sendKeys(Keys.TAB).perform(); // 鼠标通过tab要先移到富文本框中
			actions.sendKeys(
					(String) paraMap.get(TestNoticeConstants.CONTENT_TXT_FIELD))
					.perform();
			utils.pause(3000);
		}else{
			utils.input(By.xpath((String) elemMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD)), (String) paraMap
					.get(TestNoticeConstants.EDIT_TITLE_TXT_FIELD));
			utils.click(By.xpath((String) elemMap
					.get(TestNoticeConstants.TITLE_TXT_FIELD)));
			Actions actions = new Actions(utils.getDriver());
			actions.sendKeys(Keys.TAB).perform(); // 鼠标通过tab要先移到富文本框中
			actions.sendKeys(
					(String) paraMap.get(TestNoticeConstants.EDIT_CONTENT_TXT_FIELD))
					.perform();
			utils.pause(3000);
		}
		
	}

	public void intoNotice() {
		utils.pause(3000);
		utils.click(By.xpath((String) elemMap.get(TestNoticeConstants.NOTICE)));
	}

	public void clickSendNewNoticeButton() {
		do {
			utils.click(By.xpath((String) elemMap
					.get(TestNoticeConstants.SEND_NEW_NOTICE)));
			utils.pause(3000);
		} while (!utils.isElementPresent(By.xpath((String) elemMap
				.get(TestNoticeConstants.TITLE_TXT_FIELD))));
	}

	public void chooseReceiver() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.CHOOSE_PERSON_BUTTON)));
		utils.pause(3000);
		utils.switchToFrame(By.xpath((String) elemMap
				.get(TestNoticeConstants.PERSON_TREE_FRAME)));
		WebElement receiver01 = utils.getDriver().findElement(
				By.xpath((String) elemMap
						.get(TestNoticeConstants.RECEIVER_BOX_01)));
		WebElement receiver02 = utils.getDriver().findElement(
				By.xpath((String) elemMap
						.get(TestNoticeConstants.RECEIVER_BOX_02)));
		if (!receiver01.isSelected()) {
			receiver01.click();
			Assert.assertEquals(receiver01.isSelected(), true);
		}
		if (!receiver02.isSelected()) {
			receiver02.click();
			Assert.assertEquals(receiver02.isSelected(), true);
		}else {
			receiver02.click();
			Assert.assertEquals(receiver02.isSelected(), false);
		}
		
		utils.getDriver().switchTo().defaultContent();
	}

	public void choosePriority() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.PRIORITY_NORMAL)));
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.PRIORITY_IMPORTANT)));
	}

	public void chooseReplyFlag() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.REPLY_FLAG)));
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.READ_REPLY_FLAG)));
	}

	public void clickSendButton() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.SEND_BUTTON_01)));

	}
	
	public void clickSendButtonAfterEdit() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.SEND_BUTTON_02)));

	}

	public void sendStatus() {
		try {
			Assert.assertEquals(utils.getWebText(By.xpath((String) elemMap
					.get(TestNoticeConstants.FIRST_MESSAGE_STATUS))), "已发送");
			Reporter.log("message has been send.");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}
	
	public void sendStatusAfterEdit() {
		try {
			Assert.assertEquals(utils.getWebText(By.xpath((String) elemMap
					.get(TestNoticeConstants.FIRST_MESSAGE_STATUS))), "已修改");
			Reporter.log("message has been send.");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}

	public void clickOkButton02() {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.OK_BUTTON_02)));

	}

	public void uploadFile(HashMap<String, Object> paraMap) {
		utils.click(By.xpath((String) elemMap
				.get(TestNoticeConstants.UPLOAD_BUTTON)));
		utils.pause(3000);
		utils.getDriver()
				.switchTo()
				.alert()
				.sendKeys((String) paraMap.get(TestNoticeConstants.UPLOAD_FILE));
		utils.getDriver().switchTo().alert().accept();
		utils.pause(5000);
	}

	public String[] receiveTime() {
		String[] time={
		utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TIME))),
		utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TIME_02)))
		};
		return time;
	}

	public String beforeReadStatus() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.BEFORE_READ_STATUS)));
	}

	public String afterReadStatus() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.AFTER_READ_STATUS)));
	}

	public String receiveTitle() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TITLE)));
	}

	public String receiveContent() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_CONTENT)));
	}
	
	public void clickMessage(){
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.RECEIVE_TITLE)));
	}
	
	public void receiveContentFrame(){
		utils.switchToFrame(By.xpath((String)elemMap.get(TestNoticeConstants.RECFRAME)));
	}
	
	public void receiveContentFrameInTip(){
		utils.switchToFrame(By.xpath((String)elemMap.get(TestNoticeConstants.RECFRAME_IN_TIP)));
	}
	
	public void backToReceiveBox(){
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.BACK_TO_RECEIVE_BOX)));
	}

	public String receiveTipTitle() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TIP_TITLE)));
	}

	public String receiveTipContent() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TIP_CONTENT)));
	}

	public Boolean getMsgFlag() {
		WebDriverWait wait = new WebDriverWait(utils.getDriver(), 5);
		Boolean msgFlag = wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.findElement(By.xpath((String)elemMap
						.get(TestNoticeConstants.MSG_FLAG)))
						.getAttribute((String)elemMap
								.get(TestNoticeConstants.MSG_FLAG_ATTRIBUTE)).contains((String)elemMap
										.get(TestNoticeConstants.MSG_FLAG_CLASS));
			}
		});
		return msgFlag;
	}

	public WebElement getMsgNum() {
		WebDriverWait wait = new WebDriverWait(utils.getDriver(), 5);
		WebElement msgNum = wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath((String)elemMap
						.get(TestNoticeConstants.MSG_NUM)));
			}
		});
		return msgNum;
	}

	public void clickMsgTip() {
		((JavascriptExecutor)utils.getDriver()).executeScript("arguments[0].click();",utils.getDriver().findElement((By.xpath((String)elemMap
				.get(TestNoticeConstants.MSG_TIP)))));
		
	}

	public void clickFirstMsgTip() {
		utils.pause(3000);
		new Actions(utils.getDriver()).doubleClick(utils.getDriver().findElement((By.xpath((String)elemMap
				.get(TestNoticeConstants.MSG_TIP_FRIST))))).build().perform();
//		((JavascriptExecutor)utils.getDriver()).executeScript("arguments[0].dblclick();",utils.getDriver().findElement((By.xpath((String)elemMap
//				.get(TestNoticeConstants.MSG_TIP_FRIST)))));
		
	}

	public String receiveTimeInTip() {
		return utils.getWebText(By.xpath((String) elemMap
				.get(TestNoticeConstants.RECEIVE_TIP_TIME)));
	}

	public void clickSendBox() {
		utils.pause(2000);
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.SEND_BOX)));
		utils.pause(2000);
	}

	public void clickFirstEdit() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.FIRST_EDIT)));
		
	}

	public void clickFirstDelete() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.FIRST_DELETE)));
		
	}

	public void clickFirstStatic() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.FIRST_STATIC)));
		
	}

	public void clickCancelButton() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.CANCEL_BUTTON)));
	}


	public void clickReadFlag() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.READ_FLAG)));
		
	}

	public void clickTimeFlag() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.TIME_FLAG)));
		
	}

	public void closeStaticWindow() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.CLOSE_STATIC_WINDOW)));
		
	}

	public void closeMsgWindow() {
		utils.click(By.xpath((String)elemMap
				.get(TestNoticeConstants.CLOSE_MSG_WINDOW)));
		
	}
	
	
}
