package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		// driver.findElement(By.cssSelector(or.getProperty("addCustBtn"))).click();
		// driver.findElement(By.cssSelector(or.getProperty("firstName"))).sendKeys(firstName);
		// driver.findElement(By.cssSelector(or.getProperty("lastName"))).sendKeys(lastName);
		// driver.findElement(By.cssSelector(or.getProperty("postCode"))).sendKeys(postCode);
		// driver.findElement(By.cssSelector(or.getProperty("addCBtn"))).click();
		click("addCustBtn_CSS");
		type("firstName_CSS", data.get("firstname"));
		type("lastName_XPATH", data.get("lastname"));
		type("postCode_CSS", data.get("postcode"));
		click("addCBtn_CSS");

		// Alert alert = driver.switchTo().alert();

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		Thread.sleep(500);
		alert.accept();
		// Assert.fail("Add customer not successful");
		Thread.sleep(600);
	}

}
