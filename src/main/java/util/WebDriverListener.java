package util;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class WebDriverListener implements IInvokedMethodListener {

	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
		if (method.isTestMethod()) {
			WebDriver driver = LocalDriverManager.getDriver();
			if (driver != null) {
				driver.close();
				driver.quit();
			}
		}
	}

	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
		if (method.isTestMethod()) {
			String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
			WebDriver driver = LocalDriverFactory.createInstance(browserName);
			LocalDriverManager.setDriver(driver);
		}
	}
}