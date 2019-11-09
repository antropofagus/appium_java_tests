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
import java.util.List;

public class FirstTest {
    private AppiumDriver<WebElement> driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "/Users/elcentipede/IdeaProjects/appium_java_test/apks/org.wikipedia.apk");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("appPackage", "org.wikipedia");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
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

    @Test
    public void searchResultsShouldNotBeDisplayedAfterSearchCancel() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can`t find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Can`t find search input",
                "Java",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, 'Java')]"),
                "Can`t find search result",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can`t find 'x' button",
                5
        );
        Assert.assertTrue("search result is still visible",
                waitForElementNotPresent(
                    By.id("org.wikipedia:id/fragment_search_results"),
                    "Search results are still visible",
                    5
            )
        );
    }

    @Test
    public void testSearchResultsAreRelevant()
    {
        String search_string = "Java";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Cannot find search input",
                search_string,
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results",
                5
        );

        List<WebElement> search_results = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertTrue("Not all search results are relevant", checkAllSearchResultsContainTheSubstring(search_results, search_string));

    }

    private boolean checkAllSearchResultsContainTheSubstring(List<WebElement> search_results, String search_substring) {
            for(WebElement element : search_results)
            {
                if(!element.getText().contains(search_substring))
                    return false;
            }
            return true;
    }


    private WebElement waitForElementPresent(By by, String error_message, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }
    private WebElement waitForElementAndClick(By by, String error_message, long timeout) {
        WebElement element = waitForElementPresent(by, error_message, timeout);
        element.click();
        return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String error_message, String value, long timeout) {
         WebElement element = waitForElementPresent(by, error_message, timeout);
         element.sendKeys(value);
         return element;
    }


}
