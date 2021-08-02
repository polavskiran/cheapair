package pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightsReview {

	WebDriver driver;

	@FindBy(xpath = "(//div[contains(@class,'cDWdpY')])[1]")
	WebElement date1;

	@FindBy(xpath = "(//div[contains(@class,'cDWdpY')])[2]")
	WebElement date2;

	public FlightsReview(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getDate1() throws ParseException {
		String strDate1;
		
		strDate1 = date1.getText();
		String[] fromDate = strDate1.split("-");
		String tempDate = fromDate[1].replace("th,","");
		Date strDate = new SimpleDateFormat("MMM dd yyyy").parse(tempDate.trim());	// Nov 15 2021
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		strDate1 = sdf.format(strDate);
		System.out.println("tempDate= "+strDate1);
		
		return strDate1;
	}
	
	public String getDate2() throws ParseException {
		String strDate2;
		
		strDate2 = date1.getText();
		String[] fromDate = strDate2.split("-");
		String tempDate = fromDate[1].replace("th,","");
		Date strDate = new SimpleDateFormat("dd MMM yyyy").parse(tempDate);	// Nov 15 2021
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		strDate2 = sdf.format(strDate);
		System.out.println("tempDate= "+strDate2);
		
		return strDate2;
	}
}