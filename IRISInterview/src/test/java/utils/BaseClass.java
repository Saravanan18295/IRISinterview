package utils;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * Description : This class contain actions to initialize driver, to read property file,
 * to close driver
 * @author GMS Automation Team
 * @version 0.1
 */
public class BaseClass {
	public static WebDriver driver;
	public static String searchvalue;
	public static String Selectgroupvalue;
	public static String SelectMonthvalue;
	public static String LastName;
	public static String PostalCode;
	public static String CurrentURL;
	public ExtentTest test;
	public ExtentReports report;

	/**
	**********************************************************************
	* @MethodName : openBrowser()
	* @Description : This function is used to delete all cookies at the start of each scenario to avoid
	* shared state between tests
	* @return static
	***********************************************************************
	*/
	
	@Parameters("browser")
	@Test
	//Instantiate driver
	public void OpenBrowser(@Optional("chrome") String browser) {
		try {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		if (browser == null) {
			browser = "chrome";
		}
		if (browser.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("headless");
			options.setPageLoadStrategy(PageLoadStrategy.NONE); 
			options.addArguments("--start-maximized");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-features=VizDisplayCompositor");
			WebDriverManager.chromedriver().setup(); 
			driver = new ChromeDriver(options);
		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browser.equals("phantomjs")) {
			WebDriverManager.phantomjs().setup();
			driver = new PhantomJSDriver();
		}else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(dc);
		}else if (browser.equals("opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}
		driver.manage().deleteAllCookies();
		System.out.println("suxxess");
		test.log(LogStatus.PASS, "Succesfully Launched the browser: " + browser);
	} catch (Exception e) {
		test.log(LogStatus.SKIP,"Test Skipped" + e.getMessage());
		Assert.fail("Failed in initialize browser due to exception: " +e.getMessage());
	}
	}	
	
	@Test
	public void TestData() throws InvalidFormatException, IOException {	
		ExcelReader reader = new ExcelReader();
		String userDir = System.getProperty("user.dir");
		List<Map<String,String>> testData = 
				reader.getData(userDir+"\\Data\\SwagLab.xlsx", "Data");
		searchvalue = testData.get(0).get("Search value");
		Selectgroupvalue = testData.get(0).get("Select text");
		SelectMonthvalue = testData.get(0).get("Select Month");
		LastName = testData.get(0).get("LastName");
		PostalCode = testData.get(0).get("PostalCode");
		test.log(LogStatus.PASS, "Succesfully Login to the application");
	}

	@BeforeTest
	public void report() throws InterruptedException{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy_h_mm_ss a");
		String formattedDate = sdf.format(date);
		formattedDate = formattedDate.replace("-", "").replace(" ", "");
		Thread.sleep(1000);
		report = new ExtentReports(System.getProperty("user.dir")+"\\reports\\" + "_" + formattedDate + " " + "ExtentReportResults.html");
		test = report.startTest("ExtentDemo");
	}

}
