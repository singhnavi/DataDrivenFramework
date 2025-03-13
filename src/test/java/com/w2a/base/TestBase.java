package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;

public class TestBase {

	/*
	 * Initialize WebDriver - Done Properties - Done Logs Extent Reports Database
	 * Excel Mail Jenkins
	 */
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() throws IOException {
		if (driver == null) {

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);
			log.debug("Config file loaded !!!");

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			or.load(fis);
			log.debug("OR file loaded !!!");
		}

		if (config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome launched !!!");
		} else if (config.getProperty("browser").equals("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to : " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(or.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(or.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(or.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(or.getProperty(locator))).sendKeys(value);
		}
		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);
	}

	static WebElement dropdown;

	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(or.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(or.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(or.getProperty(locator)));
		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		log.debug("Test execution completed !!!");
	}

}
