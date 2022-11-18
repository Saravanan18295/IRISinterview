package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Schoolsearchpageobjects {

	@FindBy(id="main")
	public WebElement Searchtextfield;
	
	@FindBy(id="searchPostcodeButton")
	public WebElement Searchbutton;

	@FindBy(xpath="//*[text()='Contact Group']//parent::h2//following-sibling::div[@data-testid='activeIcon']")
	public WebElement ContactGroup;

	public Schoolsearchpageobjects(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
}
