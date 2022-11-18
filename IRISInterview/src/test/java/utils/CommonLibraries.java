package utils;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ReportService;



/**
 * Description : This class is created for handle all selenium actions.
 * @author Automation Team
 * @version 1.0
 */

public class CommonLibraries extends BaseClass {
	static Logger log = LogManager.getLogger(CommonLibraries.class);

	/**
	 **********************************************************************
	 * @MethodName : explicitWait()
	 * @Description : This method is used to Wait for element
	 * @param locator webElement needs to be passed
	 * @return boolean
	 ***********************************************************************
	 */
	public boolean explicitWait(WebElement locator) {
		boolean elementPresent = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, explicitwaitGlobalTime());
			if (wait.until(ExpectedConditions.visibilityOf(locator)) != null || wait.until(ExpectedConditions.elementToBeClickable(locator)) != null) {
				elementPresent = true;
			}
			takeScreenShotForPass();
		} catch (Exception e) {
			log.info("Element  not Found after waiting for 60 seconds " + e.getMessage());
			takeScreenShotForFail();
			Assert.fail("Element wait failed due to exception : " + e.getMessage());
		}
		return elementPresent;
	}

	
	// global Explict wait time for each element

	public int explicitwaitGlobalTime() {
		int secs = 50;
		return secs;
	}	

	//Method to scroll and view the element
	public void scrolltoViewElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	//Method to scroll and Locate the element
	public void scrolltoLocateElement(WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	//Method to send the values in an element
	public void sendKeys(WebElement element, String typeValue) {
		explicitWait(element);
		scrolltoLocateElement(element);
		clearElement(element);
		element.sendKeys(typeValue);
		log.info("Entered '" + typeValue + "' to input text box");
		takeScreenShotForPass();
	}


	 //Method to clear element field values
	public void clearElement(WebElement element) {
		try{
			element.sendKeys(Keys.HOME);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.chord(Keys.CONTROL, "x"));
			element.clear();
			log.info( "Input Textfiled is cleared");
		}
		catch(Exception e)
		{
			log.info("Failed while clearing field due to " + e.getMessage());
			takeScreenShotForFail();
			Assert.fail("Failed in clear element method : "+e.getMessage());
		}
	}

	//Method to print the current page title at any time
	public void getpagetitle() {
		String pagetitle = driver.getTitle();
		log.info("Current page title is " + pagetitle);
	}
	
	
	
	 //This method is to take screenshot for Pass steps.
	public void takeScreenShotForPass() {
		String filePath = System.getProperty("user.dir") + "\\Screenshots\\";
		String fileName = null;
		try {
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String mainFolderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				File mainFolder = new File("./Screenshots/" + mainFolderName);
				if (!mainFolder.exists()) {
					mainFolder.mkdirs();
				}
				File subFolder = new File("./" + mainFolder + "/PassedScreenshots");
				if (!subFolder.exists()) {
					subFolder.mkdirs();
				}
				fileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
				FileUtils.copyFile(src, new File("./" + subFolder + "/" + fileName + ".PNG"));
				String path = filePath + mainFolderName + "\\PassedScreenshots\\" + fileName + ".PNG";
				log.info(path);
		} catch (IOException e) {
			log.info("Cature screeenshot for passed step failed");
			Assert.fail("Cature screeenshot for passed step failed due to exception: " + e.getMessage());
		}

	}

	
	 //This method is to take screenshot for Failed steps.
	public void takeScreenShotForFail() {
		String filePath = System.getProperty("user.dir") + "\\Screenshots\\";
		String fileName = null;
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String mainFolderName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			File mainFolder = new File("./Screenshots/" + mainFolderName);
			if (!mainFolder.exists()) {
				mainFolder.mkdirs();
			}
			File subFolder = new File("./" + mainFolder + "/FailedStepScreenshots");
			if (!subFolder.exists()) {
				subFolder.mkdirs();
			}
			fileName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
			FileUtils.copyFile(src, new File("./" + subFolder + "/" + fileName + ".PNG"));
			String path = filePath + mainFolderName + "\\FailedStepScreenshots\\" + fileName + ".PNG";
			log.info(path);
		} catch (IOException e) {
			log.info("Cature screeenshot for failed step failed");
			Assert.fail("Cature screeenshot for failed step failed due to exception: " + e.getMessage());
		}
	}

	//Method to Click the element
	public void clickElement(WebElement Element, String ElementName) {
		int secs = explicitwaitGlobalTime();
		try {
			WebDriverWait wait = new WebDriverWait(driver, secs);
			wait.until(ExpectedConditions.visibilityOf(Element));
			Element.click();
			takeScreenShotForPass();
			log.info("Clicked on " + ElementName + " successfully");
		}
		catch (NumberFormatException e) {
			takeScreenShotForFail();
			Assert.fail("Failed in ClickElement method. Could not be Clicked - " + ElementName);
		}
		
	}


	//This function is used to verifyElementToBeVisible
	public boolean verifyElementToBeVisible(WebElement locator, String elementName) {
		boolean elementVisible = false;
		int secs = explicitwaitGlobalTime();
		try {
			WebDriverWait wait = new WebDriverWait(driver, secs);
			if (wait.until(ExpectedConditions.visibilityOf(locator)) != null) {
				elementVisible = true;
			}
		} catch (Exception e) {
			log.info("Element " + elementName + " not Found after waiting for " + secs + " seconds");
			takeScreenShotForFail();
		}
		return elementVisible;
	}

}

