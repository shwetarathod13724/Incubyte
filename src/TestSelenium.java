import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSelenium {

    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", 
//            "C:\\selenium webdriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

    	WebDriverManager.chromedriver().setup(); // automatically downloads compatible version
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();

            // Step 1: Navigate to homepage
            driver.get("https://magento.softwaretestingboard.com/");
            waitForElement(driver, By.linkText("Create an Account")).click();

            // Step 2: Fill the registration form
            String email = "user" + System.currentTimeMillis() + "@test.com";
            String password = "Test@1234";

            waitForElement(driver, By.id("firstname")).sendKeys("Shweta");
            driver.findElement(By.id("lastname")).sendKeys("Rathod");
            driver.findElement(By.id("email_address")).sendKeys(email);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("password-confirmation")).sendKeys(password);
            driver.findElement(By.cssSelector("button[title='Create an Account']")).click();

            // Step 3: Wait for account to be created
            wait.until(ExpectedConditions.urlContains("/customer/account"));

            // Step 4: Logout
            driver.get("https://magento.softwaretestingboard.com/customer/account/logout/");
            wait.until(ExpectedConditions.urlContains("logoutSuccess"));

            // Step 5: Sign In
            driver.get("https://magento.softwaretestingboard.com/");
            waitForElement(driver, By.linkText("Sign In")).click();

            waitForElement(driver, By.id("email")).sendKeys(email);
            driver.findElement(By.id("pass")).sendKeys(password);
            driver.findElement(By.id("send2")).click();

            // Step 6: Confirm login
            WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".header.content")));

            if (welcomeMessage.getText().contains("Welcome")) {
                System.out.println("✅ Registration and login successful for: " + email);
            } else {
                System.out.println("❌ Login failed for: " + email);
            }

        } catch (Exception e) {
            System.err.println("🚨 Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // 🔁 Helper method for explicit wait
    private static WebElement waitForElement(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
