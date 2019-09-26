import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5556");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "/Users/elcentipede/IdeaProjects/appium_java_test/apks/org.wikipedia.apk");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("appPackage", "org.wikipedia");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void searchInputShouldHaveValidText() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        WebElement search_input = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
                "Can`t find search input",
                5
        );

        String search_input_text = search_input.getText();

        Assert.assertEquals(
                String.format("Can`t find expected text '%s'", search_input_text),
                search_input_text,
                "Search…"
        );

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeout) {
        WebElement element = waitForElementPresent(by, error_message, timeout);
        element.click();
        return element;
    }
}
