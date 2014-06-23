package test.testcases.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import test.utilities.CustomListener;

/*
 * 测试套件:组织管理
 * 包含各子模块：组织维护、人员分配、人员查看等
 * 此类中没有写@BeforeClass 及  @AfterClass 方法
 */
public class OrganizationSuite {

	public static void main(String args[]) {
		// suite tag
		XmlSuite suite = new XmlSuite();
		// set suite name
		suite.setName("Test OrganizationManagement");
		// set parameter tag
		HashMap<String, String> para = new HashMap<String, String>();
		para.put("baseUrl",
				"http://192.168.30.170:8887");
		para.put("organization_para",
				"/src/resources/organization/organization_para.xml");
		para.put("department_para",
				"/src/resources/organization/department_para.xml");
		suite.setParameters(para);
		// set test tag
		XmlTest testDepartment = new XmlTest(suite);
		testDepartment.setName("Test Department");
		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass(DepartmentTest.class));
		testDepartment.setXmlClasses(classes);

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		TestNG tng = new TestNG();
		// tng.setTestClasses(new Class[]{DepartmentTest.class});
		tng.setXmlSuites(suites);
		tng.run();
		
	}

}
