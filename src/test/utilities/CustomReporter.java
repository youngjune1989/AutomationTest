package test.utilities;

import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IReporter {
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		Reporter rep = new Reporter();
		// Iterating over each suite included in the test
		for (ISuite suite : suites) {
			// Following code gets the suite name
			String suiteName = suite.getName();
			// Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				Reporter.log("Passed tests for suite '" + suiteName
						+ "' is:" + tc.getPassedTests().getAllResults().size());
				Reporter.log("Failed tests for suite '" + suiteName
						+ "' is:" + tc.getFailedTests().getAllResults().size());
				Reporter.log("Skipped tests for suite '" + suiteName
						+ "' is:" + tc.getSkippedTests().getAllResults().size());
			}
		}
		
		
	}
}