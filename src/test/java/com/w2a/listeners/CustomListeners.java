package com.w2a.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.ScreenCapture;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onFinish(ITestContext arg0) {

	}

	public void onStart(ITestContext arg0) {
		test = rep.startTest(arg0.getName().toUpperCase());
	}

	public void onSkipped(ITestContext arg0) {
		test.log(LogStatus.SKIP, arg0.getName().toUpperCase() + " skipped the test as runmode is No.");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailure(ITestResult arg0) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Capturing screenshot");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.log(LogStatus.FAIL, arg0.getName().toUpperCase() + " Failed with exception: " + arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		// Reporter.log("<a target=\"_blank\"
		// href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log(
				"<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a><img target=\"_blank\" src="
						+ TestUtil.screenshotName + " height=200 width=200>Screenshot</img>");

		rep.endTest(test);
		rep.flush();
	}

	public void onTestSuccess(ITestResult arg0) {
		test.log(LogStatus.PASS, arg0.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();
	}
}
