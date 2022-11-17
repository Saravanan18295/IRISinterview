package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPageObjects {

	@FindBy(xpath="//div[@class='bg-light']/div/div[2]/h1")
	public WebElement NewsSections;
	
	@FindBy(xpath="//h2[text()='November 2021']")
	public WebElement MonthSelection;

	@FindBy(xpath="//div[@class='bg-light']/div/div[text()='Finished loading']")
	public WebElement Bodyofpage;
	
	public InventoryPageObjects(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
}
