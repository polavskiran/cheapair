package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class FlightResults {

	WebDriver driver;

	@FindBy(xpath="//div[contains(@class,'cMhyTO')]")
	WebElement txtSortBy;
	
	@FindBy(xpath="//div[contains(@id,'tippy')]//div[contains(@class,'LgecV bMaJWC')]")
	List<WebElement> sortElements;
	
	@FindBy(xpath="//button[contains(@class,'CloseButton')]")
	WebElement popupClose;
	
	@FindBy(xpath="//div[@height='100%']//div[contains(@class,'src__Box-')]/span[@class='sc-jEUCeg bdchfW']/span")
	List<WebElement> prices;
	
	public FlightResults(WebDriver driver) {
		this.driver	=	driver;
		PageFactory.initElements(driver, this);
	}
	
	public void selectSortByPrice() throws InterruptedException {
		
		// Verify page title
		assertTitle();
		
		if(popupClose.isDisplayed() && popupClose.isEnabled()) {
			popupClose.click();
		}
		Thread.sleep(5000);
		
		txtSortBy.click();
		
		int size = sortElements.size();
		for(int i=1; i <= size; i++) {
			String sortText = sortElements.get(i).getText();
			if(sortText.equalsIgnoreCase("Price")) {
				sortElements.get(i).click();
				break;
			}
		}
	}
	
	private void assertTitle() {
		Assert.assertEquals("Flight Results | CheapAir",driver.getTitle());
	}
	
	public void selectFirstPrice() {
		
		for(int i=1; i < prices.size();i++) {
			prices.get(i).click();
			break;
		}
	}
}