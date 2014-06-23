package test.testcases.notice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import test.tasks.CommonTasks;
import test.tasks.notice.TestNoticeConstants;
import test.tasks.notice.TestNoticeTasks;
import test.utilities.WebDriverUtils;
import test.utilities.XMLParser;

public class NoticeTest {

	private HashMap<String, Object> paraMap;

	private TestNoticeTasks tnTasks;

	private WebDriverUtils utils;

	private CommonTasks coTasks;

	public WebDriver driver;

	private static Logger logger = Logger.getLogger(NoticeTest.class);

	@Parameters({ "notice_para", "baseUrl" })
	@BeforeClass(groups = {"checkintest","functiontest"})
	public void setup(String paraFile, String baseUrl) {
		System.setProperty("webdriver.ie.driver",
				".\\utils\\IEDriverServer.exe");
		DesiredCapabilities capabilities = DesiredCapabilities
				.internetExplorer();
		capabilities
				.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
		driver = new InternetExplorerDriver(capabilities);
		utils = new WebDriverUtils(driver);
		coTasks = new CommonTasks(utils);
		tnTasks = new TestNoticeTasks(utils);

		coTasks.openSite(baseUrl + "/dc_notice");
	}

	@Parameters({ "notice_para" })
	@Test(groups = {"checkintest","functiontest"})
	public void testSend(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);
		logger.info("the paraMap is" + paraMap);

		String sender = (String) paraMap.get(TestNoticeConstants.LOGIN_SENDER);
		String psd = (String) paraMap.get(TestNoticeConstants.LOGIN_PASSWORD);
		login(sender, psd);
		tnTasks.intoNotice();
		tnTasks.clickSendNewNoticeButton();
		tnTasks.chooseReceiver();
		tnTasks.clickOkButton02();
		tnTasks.typeTxtField(paraMap);
		tnTasks.choosePriority();
		tnTasks.chooseReplyFlag();
		tnTasks.uploadFile(paraMap);
		tnTasks.clickSendButton();
		tnTasks.sendStatus();
		coTasks.logout();
	}

	@Parameters({ "notice_para" })
	@Test(dependsOnMethods="testSend",groups = "functiontest")
	public void testEdit(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);
		// 发送者进入系统修改通知
		String sender = (String) paraMap.get(TestNoticeConstants.LOGIN_SENDER);
		String psd = (String) paraMap.get(TestNoticeConstants.LOGIN_PASSWORD);
		login(sender, psd);
		tnTasks.intoNotice();
		tnTasks.clickSendBox();
		tnTasks.clickFirstEdit();
		tnTasks.chooseReceiver();
		tnTasks.clickOkButton02();
		tnTasks.typeTxtField(paraMap);
		tnTasks.choosePriority();
		tnTasks.chooseReplyFlag();
		tnTasks.clickSendButtonAfterEdit();
		tnTasks.sendStatusAfterEdit();
		// 收件人进入通知，阅读状态为重新阅读
	}

	@Test(dependsOnMethods="testEdit",groups = "functiontest")
	public void testDelete() {
		tnTasks.clickSendBox();
		String timeBefore = tnTasks.receiveTime()[0];
		String time02 = tnTasks.receiveTime()[1];
		tnTasks.clickFirstDelete();
		tnTasks.clickCancelButton();
		tnTasks.clickFirstDelete();
		tnTasks.clickOkButton02();
		String timeAfter = tnTasks.receiveTime()[0];
		Assert.assertEquals(timeAfter, time02);
	}

	@Test(dependsOnMethods="testEdit",groups = "functiontest")
	public void testStatic() {
		tnTasks.clickSendBox();
		tnTasks.clickFirstStatic();
		utils.pause(2000);
		coTasks.filpPage();
		tnTasks.clickReadFlag();
		tnTasks.clickTimeFlag();
		tnTasks.closeStaticWindow();
	}

	@Parameters({ "notice_para" })
	@Test(dependsOnMethods="testSend",groups = "checkintest")
	public void testReceiveTip(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		paraMap = (HashMap<String, Object>) XMLParser.getInstance().parserXml(
				paraFile);

		String receiver01 = (String) paraMap
				.get(TestNoticeConstants.LOGIN_RECEIVER_01);
		String psd = (String) paraMap.get(TestNoticeConstants.LOGIN_PASSWORD);
		login(receiver01, psd);

		String title = (String) paraMap
				.get(TestNoticeConstants.TITLE_TXT_FIELD);
		String content = (String) paraMap
				.get(TestNoticeConstants.CONTENT_TXT_FIELD);
		// message tip
		try {
			Boolean msgFlag = tnTasks.getMsgFlag();
			WebElement msgNum = tnTasks.getMsgNum();
			Reporter.log("Recieve message : " + msgFlag + " Num : "
					+ msgNum.getText());
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
		tnTasks.clickMsgTip();
		tnTasks.clickFirstMsgTip();

		String getCurrentWindowId = driver.getWindowHandle();
		String time = tnTasks.receiveTimeInTip();
		try {
			Assert.assertEquals(tnTasks.receiveTipTitle(), title);
			Reporter.log("Title assert success.");
			tnTasks.receiveContentFrameInTip();
			Assert.assertEquals(tnTasks.receiveTipContent(), content);
			driver.switchTo().window(getCurrentWindowId);
			Reporter.log("Content assert success.");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
		
	}

	@Parameters({ "notice_para" })
	@Test(dependsOnMethods="testSend",groups = "checkintest")
	public void testReceive(String paraFile) {
		logger.info("Current method : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		String receiver02 = (String) paraMap
				.get(TestNoticeConstants.LOGIN_RECEIVER_02);
		String psd = (String) paraMap.get(TestNoticeConstants.LOGIN_PASSWORD);
		login(receiver02, psd);
		tnTasks.intoNotice();
		String time = tnTasks.receiveTime()[0];
		String title = (String) paraMap
				.get(TestNoticeConstants.TITLE_TXT_FIELD);
		String content = (String) paraMap
				.get(TestNoticeConstants.CONTENT_TXT_FIELD);
		try {
			Assert.assertEquals(tnTasks.receiveTitle(), title);
			Reporter.log("Title assert success");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			Assert.assertEquals(tnTasks.beforeReadStatus(), "未阅读");
			Reporter.log("(Before read)Status assert success");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			tnTasks.clickMessage();
			utils.pause(3000);
			tnTasks.receiveContentFrame();
			Assert.assertEquals(tnTasks.receiveContent(), content);
			utils.getDriver().switchTo().defaultContent();
			Reporter.log("Content assert success.");
		} catch (Exception e) {
			// TODO: handle exception
		}
		utils.pause(2000);
		tnTasks.backToReceiveBox();
		coTasks.logout();
	}

	@AfterClass(groups = {"checkintest","functiontest"})
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		
	}

	private void login(String user, String psd) {
		System.out.println("clear cookie");
		this.driver.manage().deleteAllCookies();
		System.out.println("refresh");
		this.driver.navigate().refresh();
		tnTasks.selectSchool();
		utils.input(By.xpath(tnTasks.getUserNamePath()), user);
		utils.input(By.xpath(tnTasks.getPsdPath()), psd);
		utils.click(By.xpath(tnTasks.getSubmitPath()));
		utils.pause(3000);
	}
	
	
	private void doDelete(){
		  String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动
		  String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=dc_notice";  //连接服务器和数据库test
		  String userName = "sa";  //默认用户名
		  String userPwd = "hzth-801";  //密码
		  Connection dbConn;
		  try {
		   Class.forName(driverName);
		   dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		   System.out.println("Connection Successful!");  //如果连接成功 控制台输出Connection Successful!
		   String sql_1 = "delete from no_receivenotice";
		   String sql_2 = "delete from no_notice";
		   Statement stmt=dbConn.createStatement();// 执行SQL语句
		   stmt.executeUpdate(sql_1);
		   stmt.executeUpdate(sql_2);
		   System.out.println("删除数据成功");
		   stmt.close();
		   dbConn.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		}
	
}
