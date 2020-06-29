import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import utility.Utility;


public class CarFax {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"/Users/anarganbarov/Documents/SeleniumFiles/BrowserDriver/chromedriver3");
		// 1. Open the chrome browser
		WebDriver driver = new ChromeDriver();

		// 2. Go to carfax.com
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.carfax.com");

		// 3. Click on Find Used Car

		driver.findElement(By.xpath("//a[@class='hero__button button--green']")).click();

		String expected = driver.findElement(By.xpath("//h1[contains(text(),'Shopping for a Used Car?')]")).getText();
		String actual = "Shopping for a Used Car?";
		// Verify the page title contains the word "Used Cars"
		Utility.assertEquals(expected, actual);
		// Choose "Tesla "for Make
		driver.findElement(By.xpath("//select[@name='make']")).click();

		Select car = new Select(driver.findElement(By.xpath("//select[@name='make']")));
		car.selectByValue("Tesla");

		// Choose “Model S” for Model.
		Select model = new Select(driver.findElement(By.xpath("//select[@name='model']")));
		model.selectByValue("Model S");
		// Enter the zipcode as 22182 and click Next
		driver.findElement(By.xpath("//input[@placeholder='Zip Code']")).sendKeys(Keys.chord("22182") + Keys.ENTER);


		// Utility.assertEquals(expected1, actual1);

		// Click on all 4 checkboxes.
		driver.findElement(By.xpath(
				"//label[@class='checkbox checkbox-styled checkbox-list-item--label noAccidents']//span[@class='checkbox-list-item--fancyCbx']"))
		.click();
		driver.findElement(By.xpath(
				"//label[@class='checkbox checkbox-styled checkbox-list-item--label oneOwner']//span[@class='checkbox-list-item--fancyCbx']"))
		.click();
		driver.findElement(By.xpath("//li[3]//label[1]//span[2]")).click();
		driver.findElement(By.xpath("//li[4]//label[1]//span[2]")).click();

		WebElement button = driver.findElement(By.xpath("//span[@class='totalRecordsText']"));
		button.click();

		String expected2 = driver.findElement(By.xpath("//span[@class='totalRecordsText' and xpath=\"1\">9]"))
				.getText();

		String actual2 = "//span[@id='totalResultCount' and xpath=\"1\">9]";
		// Verify the page title contains the word "Used Cars"
		Utility.assertContains(expected2, actual2);

		int count = 0;
		for (int i = 1; i <= 10; i++) {

			System.out.println(
					driver.findElement(By.xpath(("(//h4[@class='srp-list-item-basic-info-model'])[" + i + "]")))
					.getText().contains("Tesla Model S"));
			count++;
		}
		//System.out.println(Integer.toString(count).equals("Tesla Model S"));

		// Get price of each result and save them into a list in the order of their
		// appearance

		driver.findElement(By.xpath("//select[@class='srp-header-sort-select']")).click();

		driver.findElement(By.id("priceDesc")).click();
		//		   

		List<WebElement> spans = driver.findElements(By.xpath("(//span[@class='srp-list-item-price'])"));

		List<Double> actualValues = new ArrayList<Double>();

		for (WebElement webElement : spans) {

			actualValues.add(Double.parseDouble(webElement.getText().replace("Price: $", "").replace(",", "")));

		}



		List<Double> expectedValues = new ArrayList<Double>(actualValues);

		Collections.sort(expectedValues);

		// System.out.println(expectedValues);

		Collections.reverse(expectedValues);
		System.out.println(expectedValues);

		if (!expectedValues.equals(actualValues)) {

			System.out.println("PASS");
		} else {
			System.out.println("FAIL");

			// Choose the Mileage

		}

		driver.findElement(By.id("mileageAsc")).click();

		for (int i = 1; i <= 37; i += 4) {

			List<WebElement> actualMileage = driver
					.findElements(By.xpath("(//span[@class='srp-list-item-basic-info-value'])[" + i + "]"));

			List<Double> actualMileage1 = new ArrayList<Double>();

			for (WebElement webElement : actualMileage) {

				actualMileage1.add((Double.parseDouble(
						webElement.getText().replace("Mileage:", "").replace(",", "").replace("miles", ""))));

			}
			List<Double> expectedMileage = new ArrayList<Double>(actualMileage1);

			// Verify that the results are displayed from low to high

			Collections.sort(expectedMileage);

			System.out.print(expectedMileage);
		}

		System.out.println();

		// Choose "Year- New to Old " option from sort menu
		driver.findElement(By.id("yearDesc")).click();

		List<WebElement> model1 = driver.findElements(By.xpath("//h4[@class='srp-list-item-basic-info-model'] "));

		// Verify that the results are displayed from new to old year
		List<Double> actualYear = new ArrayList<Double>();

		for (WebElement webElement : model1) {

			actualYear.add(Double.parseDouble(webElement.getText().substring(0, 4)));
		}

		List<Double> expectedYear = new ArrayList<Double>(actualYear);

		Collections.sort(expectedYear);
		// System.out.println(expectedYear);

		Collections.reverse(expectedYear);

		System.out.println(expectedYear);

		if (!expectedYear.equals(actualYear)) {

			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}

	}

}
