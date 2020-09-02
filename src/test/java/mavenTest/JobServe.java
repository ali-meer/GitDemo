package mavenTest;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JobServe {

	WebDriver driver;
	String url = "https://www.jobserve.com/gb/en/Job-Search/";
	String keywords = "test engineer";
	String location = "london";
	String age = "Today";

	@BeforeTest
	public void beforeTest() {

		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		driver = new FirefoxDriver();
	}

	@Test(priority = 1)
	public void testKeyword() {

		try {

			driver.navigate().to(url);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// input keyword and location
			driver.findElement(By.xpath("//input[@id='txtKey']")).sendKeys(keywords);
			driver.findElement(By.xpath("//input[@id='txtLoc']")).sendKeys(location);

			// select Today
			Select jobAge = new Select(driver.findElement(By.xpath("//*[@id='selAge']")));
			jobAge.selectByVisibleText(age);

			// Validate - keyword
			driver.findElement(By.xpath("//input[@id='btnSearch']")).click();

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@id='resultrole']")))).isDisplayed();

			Assert.assertEquals(driver.findElement(By.xpath("//*[@id='resultrole']")).getText().trim(), keywords);

			System.out.println("Test 1 Passed");

		} catch (Exception e) {
			System.out.println("Test 1 Failed");
			Assert.fail(e.getMessage());
		}

	}

	@Test(priority = 2)
	public void testLocation() {

		// Validate - Location
		try {
			driver.findElement(By.xpath("//*[@class='locationbold']")).isDisplayed();

			Assert.assertEquals(driver.findElement(By.xpath("//*[@class='locationbold']")).getText().trim(), location);

			System.out.println("Test 2 Passed");
		} catch (Exception e) {
			System.out.println("Test 2 Failed");
			Assert.fail(e.getMessage());
		}

	}

	@Test(priority = 3)
	public void testJobTitle() {

		// Validate - Job Title
		try {

			String str = driver.findElement(By.xpath("//*[@id='td_jobpositionlink']")).getText();

			System.out.println(str.contains(keywords));

			Assert.assertTrue(str.contains(keywords));

			System.out.println("Test 3 Passed");
		} catch (AssertionError e) {
			System.out.println("Test 3 Failed");
			Assert.fail(e.getMessage());
		}

	}

	@AfterTest
	public void afterTest() {

		driver.close();
	}

}
