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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageObjects.InventoryPageObjects;
import pageObjects.Schoolsearchpage;
import utils.BaseClass;
import utils.CommonLibraries;

public class VerifyQATest extends BaseClass{
	static Logger log = LogManager.getLogger(VerifyQATest.class);
	CommonLibraries commonlibraries = new CommonLibraries();
	Schoolsearchpage schoolsearchpage;
	InventoryPageObjects inventorypageobjects;

	@Test
	public void aSearch() throws InterruptedException, InvalidFormatException, IOException{
		try{
		schoolsearchpage = new Schoolsearchpage(driver);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get("https://osa-web.t-cg.co.uk/");
		driver.manage().window().maximize();
		commonlibraries.getpagetitle();
		commonlibraries.explicitWait(schoolsearchpage.Searchtextfield);
		commonlibraries.sendKeys(schoolsearchpage.Searchtextfield, searchvalue);
		commonlibraries.clickElement(schoolsearchpage.Searchbutton, "Search");
		test.log(LogStatus.PASS, "Succesfully Logged in to the application");
		commonlibraries.takeScreenShotForPass();
	}
	catch(Exception e) {
		test.log(LogStatus.SKIP,"Test Skipped");
		commonlibraries.takeScreenShotForFail();
		Assert.fail("failed to login due to the exception "+ e.getMessage());
		}
	}
	
	@Test
	public void ScrollandSelectOrganization(){
	try {
		//Click on Add to Cart
		WebElement selectgroup = driver.findElement(By.xpath("//*[text()='"+Selectgroupvalue+"']//parent::h2//following-sibling::div[@data-testid='activeIcon']"));
		commonlibraries.scrolltoLocateElement(selectgroup);
		commonlibraries.explicitWait(selectgroup);
		commonlibraries.clickElement(selectgroup, "Select the option");
		String text = inventorypageobjects.NewsSections.getText();
		log.info(text);
		assertSame("News", text);

		WebElement SelectMonth = driver.findElement(By.xpath("//h2[text()='"+SelectMonthvalue+"']"));
		commonlibraries.scrolltoViewElement(SelectMonth);
		commonlibraries.explicitWait(SelectMonth);

		driver.get("https://osa-web.t-cg.co.uk/qatest");
		
		//Verify current URL
		CurrentURL = driver.getCurrentUrl();
		log.info(CurrentURL);
		assertTrue(CurrentURL.equalsIgnoreCase("https://osa-web.t-cg.co.uk/qatest"));

		try {
			log.info("QATest page is displayed: "+inventorypageobjects.Bodyofpage.isDisplayed());
			log.info("QATest page is enabled: "+inventorypageobjects.Bodyofpage.isEnabled());
			log.info("QATest page is loaded and Working");
			}
		catch(Exception issue) {
		if(issue.getMessage().contains("no such element")) {
			log.info("QAtest is not finished the loading");
		}
		junit.framework.Assert.fail("Failed" + issue.getMessage());
		}
	 }
		catch(Exception e) {
		test.log(LogStatus.FAIL, "Failed to complete the test case due to"+ e.getMessage());
		log.info("Execution is not completed due to: "+ e.getMessage());
		commonlibraries.takeScreenShotForFail();
		junit.framework.Assert.fail("Failed" + e.getMessage());
		}
	}	
		
	@AfterTest
	public void Teardown() throws InterruptedException{
			try {
				Thread.sleep(1000);
				report.endTest(test);
				report.flush();
				} 
			catch (Exception e) {
				log.info("Failed in createReport method due to exception: " + e.getMessage());
				commonlibraries.takeScreenShotForFail();
				junit.framework.Assert.fail("Failed" + e.getMessage());
				}
			driver.quit();
	}

}