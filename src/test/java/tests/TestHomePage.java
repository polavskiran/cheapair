package tests;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.FlightResults;
import pages.FlightsReview;
import pages.HomePage;

public class TestHomePage {

	WebDriver driver;

	HomePage homePage;
	FlightResults flightsResult;
	FlightsReview flightReview;
	// String journeyType = "OneWay";
	String fromDate;

	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(String browser) {

		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "E:\\Drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get("https://www.cheapair.com/");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "E:\\Drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.get("https://www.cheapair.com/");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters({ "journeyType", "journeyDate" })
	@Test
	public void testHomePage(String journeyType, String journeyDate) throws ParseException {

		homePage = new HomePage(driver);
		// Assert.assertEquals("Cheap Airline Tickets, Airfares & Discount Air Tickets |
		// CheapAir", driver.getTitle());
		fromDate = journeyDate;

		if (journeyType.equalsIgnoreCase("OneWay")) {

			homePage.searchFlights("New Delhi", "Mumbai", journeyDate, "");

		} else if (journeyType.equalsIgnoreCase("TwoWay")) {
			homePage.selectRoundTrip();
			homePage.selectFrom("New Delhi");
			homePage.selectTo("Mumbai");
			homePage.selectDatesOption("2021-11-15");
			homePage.selectDatesOption("2021-11-25");
		}
	}

	@Test(dependsOnMethods = { "testHomePage" })
	public void testSortByPrice() throws InterruptedException {

		flightsResult = new FlightResults(driver);
		flightsResult.selectSortByPrice();
		flightsResult.selectFirstPrice();
	}

	@Test(dependsOnMethods = { "testSortByPrice" })
	public void testDate() throws ParseException {
		flightReview = new FlightsReview(driver);

		String date1 = flightReview.getDate1();
		System.out.println("Date1 = " + date1);
		Assert.assertEquals(fromDate, date1);
	}

	@AfterTest
	public void closeBrowser() {
		if (driver != null)
			driver.quit();
	}
}