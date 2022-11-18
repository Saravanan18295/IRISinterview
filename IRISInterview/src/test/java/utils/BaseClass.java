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
 * Description : This class contain actions to initialize driver, to read excel file
 * and report
 * @author Saravanan Pazhani
 * @version 1.0
 */
public class BaseClass {
	public static WebDriver driver;
	public static String searchvalue;
	public static String Selectgroupvalue;
	public static String SelectMonthvalue;
	public static String CommonURL;
	public static String QAtestURL;
	public static String CurrentURL;
	public ExtentTest test;
	public ExtentReports report;

	/**
	**********************************************************************
	* @MethodName : openBrowser()
	* @Description : This function is used to delete all cookies at the start of each scenario to avoid
	* shared state between tests. We can use any driver based on the requirement
	* and it's configured with testng.xml as well with multiple browsers
	***********************************************************************
	*/
	
	@Parameters("browser")
	@Test
	public void AOpenBrowser(@Optional("chrome") String browser) {
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
			WebDriverManager.firefoxdriver().version("0.21.0").setup(); 
			driver = new FirefoxDriver(dc);
		}else if (browser.equals("opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}
		driver.manage().deleteAllCookies();
		test.log(LogStatus.PASS, "Succesfully Launched the browser: " + browser);
	} catch (Exception e) {
		test.log(LogStatus.SKIP,"Test Skipped" + e.getMessage());
		Assert.fail("Failed in initialize browser due to exception: " +e.getMessage());
	}
	}	
	

	/**
	**********************************************************************
	* @MethodName : TestData()
	* @Description : This function is used to fetch the excel value based on the column name and row number. 
	* We can easily iterate the test case with different data sets and very useful in BDD scenario outline. 
	* Even manual tester can handle and make changes in the automation by updating the data sheet.
	***********************************************************************
	*/
	
	@Test
	public void BTestData() throws InvalidFormatException, IOException {	
		ExcelReader reader = new ExcelReader();
		String userDir = System.getProperty("user.dir");
		List<Map<String,String>> testData = 
				reader.getData(userDir+"\\Data\\IRISTestData.xlsx", "Data");
		searchvalue = testData.get(0).get("Search value");
		Selectgroupvalue = testData.get(0).get("Select text");
		SelectMonthvalue = testData.get(0).get("Select Month");
		CommonURL = testData.get(0).get("CommonURL");
		QAtestURL = testData.get(0).get("QAtestURL");
		test.log(LogStatus.PASS, "Succesfully Login to the application");
	}

	/**
	**********************************************************************
	* @MethodName : report()
	* @Description : This function is used to capture the log in the extent report.
	***********************************************************************
	*/
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
