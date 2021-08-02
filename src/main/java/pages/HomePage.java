package pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {

	WebDriver driver;

	@FindBy(xpath = "//span[@id='tripTypeContainer']//input[@id='tripTypeOW']//following-sibling::span[@class='tgl-btn']")
	WebElement btnOneWay;

	@FindBy(xpath = "//span[@id='tripTypeContainer']//input[@id='tripTypeTR']//following-sibling::span[@class='tgl-btn']")
	WebElement btnRoundTR;

	@FindBy(id = "from1")
	WebElement txtFrom;

	@FindBy(id = "to1")
	WebElement txtTo;

	@FindBy(xpath = "//div[@id='dates']")
	WebElement selectDates;

	@FindBy(xpath = "(//ul[contains(@id,'ui-id')]/li[1]/a)[1]")
	WebElement selectOrigin;

	@FindBy(xpath = "(//ul[contains(@id,'ui-id')]/li[1]/a)[2]")
	WebElement selectDestn;

	@FindBy(xpath = "//div/a[@data-handler='prev']")
	WebElement calendarPrev;

	@FindBy(xpath = "//div/a[@data-handler='next']")
	WebElement calendarNext;

	@FindBy(xpath = "//div//a[@title='Prev']//following-sibling::div[@class='ui-datepicker-title']//span[@class='ui-datepicker-month']")
	WebElement strMonth;

	@FindBy(xpath = "//div//a[@title='Prev']//following-sibling::div[@class='ui-datepicker-title']//span[@class='ui-datepicker-year']")
	WebElement strYear;
	
	@FindBy(xpath="//button[text()='Search Flights']")
	WebElement btnSearchFlight;
	
	@FindBy(xpath="//input[@id='adults']/following-sibling::button[2]")
	WebElement btnAdultsPlus;
	
	@FindBy(xpath="//input[@id='seniors']/following-sibling::button[2]")
	WebElement btnSeniorsPlus;
	
	@FindBy(xpath="//input[@id='children']/following-sibling::button[2]")
	WebElement btnChildPlus;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectOneWay() {
		btnOneWay.click();
	}

	public void selectRoundTrip() {
		btnRoundTR.click();
	}

	public void selectFrom(String origin) {
		txtFrom.click();
		txtFrom.sendKeys(origin);

		selectOrigin.click();
	}

	public void selectTo(String destination) {
		txtTo.click();
		txtTo.sendKeys(destination);

		selectDestn.click();
	}

	/*
	 * @method selectDatesOption
	 * @argument String date
	 * @return void
	 */
	public void selectDatesOption(String date) throws ParseException {
		selectDates.click();

		String str_Date;

		Date strDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
		//System.out.println("sdf = "+sdf.toString());

		str_Date = sdf.format(strDate);
		System.out.println("str_Date= "+str_Date);

		String[] dates = str_Date.split(" ");
		String monthday = dates[0];
		String month = dates[1].trim();

		// Select the Month
		while (!strMonth.getText().equalsIgnoreCase(month)) {
			calendarNext.click();
		}

		List<WebElement> allDays = driver
				.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td[@data-event='click']"));

		for (WebElement day : allDays) {
			String sDate = day.getText();

			// Select the day
			if (sDate.equals(monthday)) {
				day.click();
				break;
			}
		}
	}
	
	private void assertTitle() {
		Assert.assertEquals("Cheap Airline Tickets, Airfares & Discount Air Tickets | CheapAir",driver.getTitle());
	}
	
	/*
	 * @method searchFlights
	 * @argument String origin
	 * @argument String destination
	 * @argument String date1
	 * @argument String date2
	 * @throws ParseException
	 */
	public void searchFlights(String origin, String destination,String date1, String date2) throws ParseException {
		
		assertTitle();
		
		// Select One-way
		this.selectOneWay();
		
		//Select Starting/Origin
		this.selectFrom(origin);
		
		//Select destination
		this.selectTo(destination);
		
		//Select dates
		this.selectDatesOption(date1);
		
		// Select Travellers
		this.select_Trevellers(2, 1, 2);
		
		String parentTab = driver.getWindowHandle();
	
		// Click on Search Flights button
		btnSearchFlight.click();
		
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		//switch to new tab
		driver.switchTo().window(tabs.get(1));
	}
	
	/*
	 * @method select_Travellers
	 * @argument int adults
	 * @argument int srCitizens
	 * @argument int child
	 * @argument int infants 
	 */
	public void select_Trevellers(int adults,int srCitizens,int child) {
		
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.elementToBeClickable(btnAdultsPlus));
		
		if(adults > 1) {
			for(int j=1; j < adults;j++) {
				btnAdultsPlus.click();
			}
		}
		
		if(srCitizens >= 1) {
			for(int j=0; j < srCitizens;j++) {
				btnSeniorsPlus.click();
			}
		}
		
		if(child >= 1) {
			for(int j=0; j < child;j++) {
				btnChildPlus.click();
			}
		}
	}
}