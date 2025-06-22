package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.time.Duration;
import org.openqa.selenium.By;


public class SignupSteps {

    WebDriver driver;
    HomePage homePage;
    CreateAccountPage createAccountPage;
    LoginPage loginPage;

    // Dynamic test data
    String email = "user" + System.currentTimeMillis() + "@test.com";
    String password = "Test@1234";

    @Given("I open the Magento home page")
    public void i_open_the_magento_home_page() {
    	System.setProperty("webdriver.chrome.driver", "C:\\selenium webdriver\\chromedriver-win64\\chromedriver.exe");
    	driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");
        homePage = new HomePage(driver);
    }

    @When("I register a new account")
    public void i_register_a_new_account() {
        homePage.clickCreateAccount();
        createAccountPage = new CreateAccountPage(driver);
        createAccountPage.register("Shweta", "Rathod", email, password);
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        String welcomeMsg = driver.findElement(By.cssSelector(".header.content")).getText();
        assert welcomeMsg.contains("Welcome") : "Registration/Login failed!";
    }

    @When("I log out")
    public void i_log_out() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/logout/");
    }

    @When("I log in again with the same credentials")
    public void i_log_in_again_with_the_same_credentials() {
        driver.get("https://magento.softwaretestingboard.com/");
        homePage.clickSignIn();
        loginPage = new LoginPage(driver);
        loginPage.login(email, password);
    }

    @Then("I should be logged in again successfully")
    public void i_should_be_logged_in_again_successfully() {
        String welcomeMsg = driver.findElement(By.cssSelector(".header.content")).getText();
        assert welcomeMsg.contains("Welcome") : "Re-login failed!";
        driver.quit();
    }
}
