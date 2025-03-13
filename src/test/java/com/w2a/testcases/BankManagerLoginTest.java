package com.w2a.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() throws InterruptedException {

		log.debug("Inside login Test");
		// driver.findElement(By.cssSelector(or.getProperty("bmlBtn"))).click();
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCustBtn_CSS"))), "Login not successful");

		Reporter.log("Login successfully executed");

		Assert.fail("Login not successful");
	}
}
