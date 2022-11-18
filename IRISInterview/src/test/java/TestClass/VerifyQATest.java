package TestClass;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageObjects.ContactgroupageObjects;
import pageObjects.Schoolsearchpageobjects;
import utils.BaseClass;
import utils.CommonLibraries;

public class VerifyQATest extends BaseClass {
	static Logger log = LogManager.getLogger(VerifyQATest.class);
	CommonLibraries commonlibraries = new CommonLibraries();
	Schoolsearchpageobjects schoolsearchpageobjects;
	ContactgroupageObjects contactgroupageObjects;

	/**
	**********************************************************************
	* @MethodName : SchoolSearch()
	* @Description : This function is used for School search.
	***********************************************************************
	*/	
	@Test(priority=0)
	public void SchoolSearch() throws InterruptedException, InvalidFormatException, IOException {
		try {
			schoolsearchpageobjects = new Schoolsearchpageobjects(driver);
			contactgroupageObjects = new ContactgroupageObjects(driver);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//			Search the URL
			driver.get(CommonURL);
			driver.manage().window().maximize();
			commonlibraries.getpagetitle();
			commonlibraries.explicitWait(schoolsearchpageobjects.Searchtextfield);
			commonlibraries.sendKeys(schoolsearchpageobjects.Searchtextfield, searchvalue);
			commonlibraries.clickElement(schoolsearchpageobjects.Searchbutton, "Search");
			
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[text()='" + Selectgroupvalue+ "']//parent::h2//following-sibling::div[@data-testid='activeIcon']")));
			WebElement selectgroup = driver.findElement(By.xpath("//*[text()='" + Selectgroupvalue+ "']//parent::h2//following-sibling::div[@data-testid='activeIcon']"));
			
			//Scroll and click the Org
			commonlibraries.scrolltoLocateElement(selectgroup);
			commonlibraries.explicitWait(selectgroup);
			commonlibraries.clickElement(selectgroup, "the Organization");
			commonlibraries.explicitWait(contactgroupageObjects.NewsSections);
			String text = contactgroupageObjects.NewsSections.getText();
			log.info("Section Name is: "+text);
			assertSame("News", text);

			WebElement SelectMonth = driver.findElement(By.xpath("//h2[text()='" + SelectMonthvalue + "']"));
			commonlibraries.scrolltoViewElement(SelectMonth);
			commonlibraries.explicitWait(SelectMonth);

			test.log(LogStatus.PASS, "Succesfully Logged in to the application");
			commonlibraries.takeScreenShotForPass();
		} catch (Exception e) {
			test.log(LogStatus.SKIP, "Test Skipped");
			commonlibraries.takeScreenShotForFail();
			Assert.fail("failed to login due to the exception " + e.getMessage());
		}
	}

	/**
	**********************************************************************
	* @MethodName : SelectOrganizationandVerify()
	* @Description : This function is used to Select the Organization and Verify the QAtest page.
	***********************************************************************
	*/
	@Test(priority=1, dependsOnMethods = { "SchoolSearch" })
	public void VerifyQATest() {
		try {
			driver.get(QAtestURL);
			
			// Verify current URL
			CurrentURL = driver.getCurrentUrl();
			log.info(CurrentURL);
			assertTrue(CurrentURL.equalsIgnoreCase(QAtestURL));

			try {
				log.info("QATest page is displayed: " + contactgroupageObjects.Bodyofpage.isDisplayed());
				log.info("QATest page is enabled: " + contactgroupageObjects.Bodyofpage.isEnabled());
				log.info("QATest page is loaded and Working");
				commonlibraries.takeScreenShotForPass();
			} catch (Exception issue) {
				if (issue.getMessage().contains("no such element")) {
					log.info("QAtest is not finished the loading");
					junit.framework.Assert.fail("Failed due to QAtest is not finished the loading" + issue.getMessage());
				}else {
					junit.framework.Assert.fail("Failed due to: " + issue.getMessage());
				}
			}
		//Once the QAtest URL issue is fixed and then we can write further steps on this scenario
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Failed to complete the test case due to: " + e.getMessage());
			log.info("Execution is not completed due to: " + e.getMessage());
			commonlibraries.takeScreenShotForFail();
			junit.framework.Assert.fail("Failed due to: " + e.getMessage());
		}
	}

	/**
	**********************************************************************
	* @MethodName : Teardown()
	* @Description : This function is used to Close the report and quit the driver.
	***********************************************************************
	*/

	@AfterTest
	public void Teardown() throws InterruptedException {
		try {
			Thread.sleep(1000);
			report.endTest(test);
			report.flush();
		} catch (Exception e) {
			log.info("Failed in createReport method due to exception: " + e.getMessage());
			commonlibraries.takeScreenShotForFail();
			junit.framework.Assert.fail("Failed" + e.getMessage());
		}
		driver.quit();
	}

}