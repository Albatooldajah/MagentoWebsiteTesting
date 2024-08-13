package magentoProjectTest;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class MyFirstTask {

	WebDriver driver = new ChromeDriver();
	String myWebsite = "https://magento.softwaretestingboard.com/"; // variable
	Random Rand = new Random();
	String password = "MyPass_5443";
	String logoutLink = "https://magento.softwaretestingboard.com/customer/account/logout/";
	String emailforSignUp = "";

	@BeforeTest
	public void mySetUp() {
		driver.manage().window().maximize();
		// put the website in variable
		driver.get(myWebsite);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

	}

	@Test(priority = 1, enabled = false)
	public void createAnAccount() {
		WebElement CreateAccount = driver.findElement(By.linkText("Create an Account"));
		CreateAccount.click();
		String[] firstNames = { "Alice", "Bob", "Charlie", "David", "Eva" };
		String[] lastNames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis" };
		int randonIndexForFirstName = Rand.nextInt(firstNames.length); // inside nextint called bound
		int randonIndexForLastName = Rand.nextInt(lastNames.length); // uses .length becuse it is static array

		WebElement firstNameInput = driver.findElement(By.id("firstname"));
		WebElement lastNameInput = driver.findElement(By.id("lastname"));
		WebElement emailAddressInput = driver.findElement(By.id("email_address"));
		WebElement passInput = driver.findElement(By.id("password"));
		WebElement passComfirmInput = driver.findElement(By.id("password-confirmation"));
		WebElement signUpButton = driver.findElement(By.cssSelector(".action.submit.primary"));

		String firstName = firstNames[randonIndexForFirstName];
		String lastName = lastNames[randonIndexForFirstName];
		String domian = "@gmail.com";
		int randomNamber = Rand.nextInt(10000);

		firstNameInput.sendKeys(firstName);
		lastNameInput.sendKeys(lastName);
		emailAddressInput.sendKeys(firstName + lastName + randomNamber + domian);
		passInput.sendKeys(password);
		passComfirmInput.sendKeys(password);
		signUpButton.click();
		emailforSignUp = firstName + lastName + randomNamber + domian;
		WebElement MassegeAsWebElement = driver.findElement(By.className("messages"));
		String TheActualMassage = MassegeAsWebElement.getText();
		String TheExptedMassage = "Thank you for registering with Main Website Store.";
		Assert.assertEquals(TheActualMassage, TheExptedMassage); // to check the test is pass or not , we can't use if
																	// statement becuase the if statment Used to
																	// implement the code, not to test the element
	}

	@Test(priority = 2, enabled = false)
	public void logout() {
		driver.get(logoutLink);
		WebElement LogoutMassege = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
		String ActualMsg = LogoutMassege.getText();
		String ExcpetedMsg = "You are signed out";
		Assert.assertEquals(ActualMsg, ExcpetedMsg);
	}

	@Test(priority = 3, enabled = false)
	public void signIn() {
		WebElement signIn = driver.findElement(By.linkText("Sign In"));
		signIn.click();
		WebElement emailSignInInput = driver.findElement(By.id("email"));
		WebElement signInButton = driver.findElement(By.cssSelector(".action.login.primary"));

		emailSignInInput.sendKeys(emailforSignUp);
		WebElement passSignInInput = driver.findElement(By.id("pass"));
		passSignInInput.sendKeys(password);
		signInButton.click();
		String WelcomeMsg = driver.findElement(By.className("logged-in")).getText();
		Boolean AcualMsg = WelcomeMsg.contains("Welcome");
		Boolean ExcptedMsg = true;

		Assert.assertEquals(AcualMsg, ExcptedMsg);

	}

	@Test(priority = 4)
	public void addMenItem() throws InterruptedException {
		WebElement SelectWomen = driver.findElement(By.id("ui-id-4"));
		SelectWomen.click();
		// Go to Hot seller
		WebElement clickHotSellers = driver.findElement(By.className("product-items"));
		List<WebElement> Allitems = clickHotSellers.findElements(By.tagName("li"));
		int totalNumberOfItems = Allitems.size();
		int randomItem = Rand.nextInt(totalNumberOfItems);
		Allitems.get(randomItem).click();

		// choose size
		WebElement theContainerOfSizes = driver.findElement(By.cssSelector(".swatch-attribute-options.clearfix"));
		List<WebElement> ListOfSizes = theContainerOfSizes.findElements(By.className("swatch-option"));
		int numberofSizes = ListOfSizes.size();
		int randomSize = Rand.nextInt(numberofSizes);
		ListOfSizes.get(randomSize).click();

		// choose color
		WebElement ColorsContainer = driver
				.findElement(By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']"));
		List<WebElement> ListOfClors = ColorsContainer.findElements(By.tagName("div"));
		int numberOfColors = ListOfClors.size();
		int randomColor = Rand.nextInt(numberOfColors);
		ListOfClors.get(randomColor).click();

		// addToCart
		WebElement AddToCartButton = driver.findElement(By.id("product-addtocart-button"));

		AddToCartButton.click();
		WebElement MessageAdded = driver.findElement(By.cssSelector(".message-success.success.message"));

		System.out.println(MessageAdded.getText().contains("You added"));

		Assert.assertEquals(MessageAdded.getText().contains("You added"), true);
		
		
		

		// review section

		driver.navigate().refresh();

		WebElement ReviewSEction = driver.findElement(By.id("tab-label-reviews-title"));

		ReviewSEction.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0,1200)");

		Thread.sleep(2000);

		WebElement RatingStars = driver.findElement(By.cssSelector(".control.review-control-vote"));
		Thread.sleep(2000);

		System.out.println(RatingStars.findElements(By.tagName("label")).size() + "*****************");
		// RatingStars.findElements(By.tagName("label")).get(2).click();

		String[] ids = { "Rating_1", "Rating_2", "Rating_3", "Rating_4", "Rating_5" };
		int randomIndex = Rand.nextInt(ids.length);
		WebElement element = driver.findElement(By.id(ids[randomIndex]));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

		// Wait for the review form to be visible
		WebElement reviewForm = driver.findElement(By.id("nickname_field"));
		reviewForm.sendKeys("Great product!");
		// Enter the review text
		WebElement reviewTextInput = driver.findElement(By.id("summary_field"));
		reviewTextInput.sendKeys("This product is amazing! I love it.");

		// Enter the review text
		WebElement reviewTex = driver.findElement(By.id("review_field"));
		reviewTex.sendKeys("This product is amazing! I love it.");

		// Submit the review
		WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"review-form\"]/div/div/button"));
		submitButton.click();
		
		WebElement MessageReview = driver.findElement(By.className("message-success"));

		System.out.println(MessageReview.getText().contains("You submitted"));

		Assert.assertEquals(MessageReview.getText().contains("You submitted"), true);
	}

}
