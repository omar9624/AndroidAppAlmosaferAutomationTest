package AppTestCases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class TestCases extends Parameters {

	@BeforeTest
	public void setup() {

		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "ERROR DEVICE");
		caps.setCapability("appPackage", "com.travel.almosafer");
		caps.setCapability("appActivity", "com.travel.splash_ui_private.di.splash.SplashActivity");
		caps.setCapability("noReset", true);
		caps.setCapability("unicodeKeyboard", "true");
		caps.setCapability("resetKeyboard", "true");

		File application = new File("src/Applications/Almosafer_Hotels.apk");

		caps.setCapability(MobileCapabilityType.APP, application.getAbsolutePath());

	}

	@Test(priority = 1)
	public void ChooseCountry() throws MalformedURLException, InterruptedException {

		driver = new AndroidDriver(new URL(url), caps);

		Thread.sleep(4000);

		if (!driver.findElements(By.id("com.travel.almosafer:id/selectCountryTV")).isEmpty()) {

			List<WebElement> countryList = driver.findElements(By.className("android.view.ViewGroup"));
			int randomNum = random.nextInt(countryList.size());
			countryList.get(randomNum).click();
			Thread.sleep(2000);

			WebElement countinueButton = driver.findElement(By.id("com.travel.almosafer:id/ctaButton"));
			countinueButton.click();
		}

	}

	@SuppressWarnings("deprecation")
	@Test(priority = 2)
	public void SigninProcess() throws MalformedURLException, InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Check If Already Signed In
		if (!driver.findElements(By.id("com.travel.almosafer:id/signUp")).isEmpty()) {

			// Navigate To Sign In Page
			WebElement signupButton = driver.findElement(By.id("com.travel.almosafer:id/signUp"));
			signupButton.click();

			WebElement signinLink = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Sign in\"]"));
			signinLink.click();

			Thread.sleep(2000);
			WebElement signinEmailTab = driver.findElement(By.xpath(
					"//android.widget.HorizontalScrollView[@resource-id=\"com.travel.almosafer:id/registrationTabLayout\"]/android.widget.LinearLayout/android.widget.LinearLayout[2]"));
			signinEmailTab.click();

			// Send Keys To Email And Password Inputs
			WebElement emailInput = driver.findElement(By.xpath(
					"//android.widget.EditText[@resource-id=\"com.travel.almosafer:id/textInputEditText\" and @text=\"Email\"]"));
			WebElement passwordInput = driver.findElement(By.xpath(
					"//android.widget.EditText[@resource-id=\"com.travel.almosafer:id/textInputEditText\" and @text=\"Password\"]"));
			WebElement signinButton = driver.findElement(By.id("com.travel.almosafer:id/btnSignIn"));

			emailInput.sendKeys("omaralsayyed24@gmail.com");
			passwordInput.sendKeys("Omar_12345");
			signinButton.click();

		} else {
			System.out.println("Already Signed In");
		}

	}

	@SuppressWarnings("deprecation")
	@Test(priority = 3)
	public void searchFlight() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Navigate To Flights Page
		WebElement flightsTab = driver.findElement(
				By.xpath("//androidx.cardview.widget.CardView[@content-desc=\"Flight\"]/android.widget.LinearLayout"));
		flightsTab.click();

		// Choose Cities And Fill Inputs That Select Where I Go
		WebElement fromInput = driver.findElement(By.xpath(
				"//androidx.cardview.widget.CardView[@resource-id=\"com.travel.almosafer:id/originView\"]/android.view.ViewGroup"));
		Thread.sleep(2000);
		fromInput.click();

		// Choose Random City
		WebElement searchBarFromCity = driver.findElement(By.id("com.travel.almosafer:id/edSearch"));
		int randomNumberOne = random.nextInt(firstCity.length);
		searchBarFromCity.click();
		searchBarFromCity.sendKeys(firstCity[randomNumberOne]);

		Thread.sleep(1000);
		WebElement firstFromResult = driver.findElement(By.xpath(
				"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvSearchAirports\"]/android.view.ViewGroup[1]"));
		firstFromResult.click();

		try {
			Thread.sleep(3000);
			WebElement searchBarGoCity = driver.findElement(By.id("com.travel.almosafer:id/edSearch"));
			int randomNumberTwo = random.nextInt(secondCity.length);
			searchBarGoCity.click();
			searchBarGoCity.sendKeys(secondCity[randomNumberTwo]);

			Thread.sleep(2000);
			WebElement firstGoResult = driver.findElement(By.xpath(
					"//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.travel.almosafer:id/rvSearchAirports\"]/android.view.ViewGroup[1]"));
			firstGoResult.click();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test(priority = 4)
	public void selectDepartureAndReturnDate() throws InterruptedException {

		// Navigate To Flights Dates Page
		WebElement departureDateCon = driver.findElement(By.xpath(
				"//androidx.cardview.widget.CardView[@resource-id=\"com.travel.almosafer:id/checkIn\"]/android.view.ViewGroup"));
		departureDateCon.click();

		// Declare The Departure Date By Using LocalDate
		LocalDate today = LocalDate.now();
		int dapartureDay = today.plusDays(1).getDayOfMonth();
		String departureMonth = today.plusDays(1).getMonth().toString();
		String departureMonthCon = departureMonth.substring(0, 1).toUpperCase()
				+ departureMonth.substring(1).toLowerCase();
		int departureYear = today.plusDays(1).getYear();

		String FullDepartureDate = dapartureDay + " " + departureMonthCon + " " + departureYear;

		System.out.println("Departure Date : " + FullDepartureDate);

		// Declare The Return Date Randomly
		int randomFlightsDuration = random.nextInt(3, 10);

		int returnDay = today.plusDays(randomFlightsDuration).getDayOfMonth();
		String returnMonth = today.plusDays(randomFlightsDuration).getMonth().toString();
		String returnMonthCon = returnMonth.substring(0, 1).toUpperCase() + returnMonth.substring(1).toLowerCase();
		int returnYear = today.plusDays(randomFlightsDuration).getYear();

		String FullReturnDate = returnDay + " " + returnMonthCon + " " + returnYear;

		// EX : 15 May - 16 May
		globalFlightDate = dapartureDay + " " + departureMonthCon + " - " + returnDay + " " + returnMonthCon;

		System.out.println("Return Date : " + FullReturnDate);

		// Declare And Choose Date By Using Dates In XPath
		WebElement departureDateButton = driver
				.findElement(By.xpath("//android.widget.CheckedTextView[@content-desc=\"" + FullDepartureDate + "\"]"));
		WebElement returnDateButton = driver
				.findElement(By.xpath("//android.widget.CheckedTextView[@content-desc=\"" + FullReturnDate + "\"]"));
		WebElement confirmDateButton = driver.findElement(By.xpath(
				"//android.view.ViewGroup[@resource-id=\"com.travel.almosafer:id/confirmBtn\"]/android.view.ViewGroup"));

		departureDateButton.click();
		returnDateButton.click();
		Thread.sleep(3000);
		confirmDateButton.click();

		Thread.sleep(2000);

		// Assertion Date
		boolean actualDepartureDate = driver.findElement(By
				.xpath("//android.widget.TextView[@resource-id=\"com.travel.almosafer:id/tvMenuItemTitle\" and @text=\""
						+ dapartureDay + " " + departureMonthCon + ", " + departureYear + "\"]"))
				.getText().contains(String.valueOf(dapartureDay));
		myAssert.assertEquals(actualDepartureDate, true, "Departure date is not correct");

		boolean actualReturnDate = driver.findElement(By
				.xpath("//android.widget.TextView[@resource-id=\"com.travel.almosafer:id/tvMenuItemTitle\" and @text=\""
						+ returnDay + " " + returnMonthCon + ", " + returnYear + "\"]"))
				.getText().contains(String.valueOf(returnDay));
		myAssert.assertEquals(actualReturnDate, true, "Return date its not correct");

	}

	@Test(priority = 5)
	public void selectPassenfersRandomly() throws InterruptedException, IOException {

		// Navigate To Passengers Page
		WebElement passengersButton = driver.findElement(
				By.xpath("//androidx.cardview.widget.CardView[@content-desc=\"paxAndClass\"]/android.view.ViewGroup"));
		passengersButton.click();

		// Declare Plus Button Element For Adults And Children And Infants
		WebElement plusAdultsBtn = driver.findElement(
				By.xpath("(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/stepUp\"])[1]"));
		WebElement plusChildrenBtn = driver.findElement(
				By.xpath("(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/stepUp\"])[2]"));
		WebElement plusInfantsBtn = driver.findElement(
				By.xpath("(//android.widget.ImageView[@resource-id=\"com.travel.almosafer:id/stepUp\"])[3]"));

		// Random Number For Passengers -- Maximum Pass = 18 -- Adults + Children <= 9
		// -- Infants <= Adults Numbers
		int randomAdults = random.nextInt(8);
		int randomCheldren = random.nextInt(8 - randomAdults);
		int randomInfants = random.nextInt(randomAdults + 1);

		for (int i = 0; i < 8; i++) {
			if (i <= randomAdults) {
				plusAdultsBtn.click();
			}
			if (i <= randomCheldren) {
				plusChildrenBtn.click();
			}
			if (i <= randomInfants) {
				plusInfantsBtn.click();
			}
		}

		Thread.sleep(2000);
		List<WebElement> PassengersNumber = driver
				.findElements(By.xpath("//android.widget.TextView[@resource-id='com.travel.almosafer:id/count']"));
		System.out.println(PassengersNumber.size());

		boolean actualPassengersNumber = Integer.valueOf(PassengersNumber.get(0).getText())
				+ Integer.valueOf(PassengersNumber.get(1).getText())
				+ Integer.valueOf(PassengersNumber.get(2).getText()) < 18;

		boolean actualAdultsAndChildrenNumber = Integer.valueOf(PassengersNumber.get(0).getText())
				+ Integer.valueOf(PassengersNumber.get(1).getText()) <= 9;

		boolean actualInfantsNumber = Integer.valueOf(PassengersNumber.get(2).getText()) <= Integer
				.valueOf(PassengersNumber.get(0).getText());

		int numberOfPassengers = Integer.valueOf(PassengersNumber.get(0).getText())
				+ Integer.valueOf(PassengersNumber.get(1).getText())
				+ Integer.valueOf(PassengersNumber.get(2).getText());

		myAssert.assertEquals(actualPassengersNumber, true, "The number of passengers greater than 18");
		myAssert.assertEquals(actualAdultsAndChildrenNumber, true,
				"The number of Adults and Childrens not less than or equal 9");
		myAssert.assertEquals(actualInfantsNumber, true, "The number of Infants greater than Adults number");

		Thread.sleep(3000);
		WebElement applyButton = driver.findElement(By.id("com.travel.almosafer:id/btnApply"));
		applyButton.click();

		// Complete Search Flights
		Thread.sleep(2000);
		WebElement searchButton = driver.findElement(By.id("com.travel.almosafer:id/btnFlightSearch"));
		searchButton.click();

		// Get Result of Passengers number And Date
		Thread.sleep(2000);
		String resultSearch = driver.findElement(By.id("com.travel.almosafer:id/tvFlightToolbarSubTitle")).getText();

		String actualResult = numberOfPassengers + " Travellers - " + globalFlightDate;

		// Assertion The Result
		myAssert.assertEquals(actualResult, resultSearch);

		// Screenshot For The Result
		Thread.sleep(12000);
		TakeScreenshot();
	}
}
