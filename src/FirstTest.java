import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

    @Test
    public void testSaveTwoArticlesToMyList()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        String search_word = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Can`t find search input",
                search_word,
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can`t find 'Object-oriented programming language' article",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can`t find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can`t find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can`t find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can`t find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can`t find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Can`t put text into articles folder input",
                name_of_folder,
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Can`t press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can`t find 'x' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can`t find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Can`t find search input",
                search_word,
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='JavaScript']"),
                "Can`t find 'JavaScript' article",
                5
        );

        String article_title = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can`t find 'JavaScript' article title",
                30
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can`t find button to open 'JavaScript' article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can`t find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Can`t find '" + name_of_folder + "' folder",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can`t find 'x' button",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can`t find navigation button to My lists",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/reading_list_list"),
                "Can`t find article folders list",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Can`t find created folder",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "'Java (programming language)' article is not found in the list",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@text='JavaScript']"),
                "'JavaScript' article is not found in the list",
                15
        );

        int articles_amount = getAmountOfElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")
        );

        Assert.assertEquals(
                "The number of articles is not as expected: " + articles_amount,
                2,
                articles_amount
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can`t find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can`t delete saved article",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='JavaScript']"),
                "'JavaScript' article is not found in the list",
                30
        );

        String article_title_opened_from_list = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Can`t find 'JavaScript' article title",
                30
        );

        Assert.assertEquals("Article titles do not match",
                article_title,
                article_title_opened_from_list);
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
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    //swipe methods
    protected void swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }
    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes)
    {
        int already_swiped = 0;

        while (driver.findElements(by).size() == 0)
        {
            if(already_swiped > max_swipes)
            {
                waitForElementPresent(by, "Cannot find element by swiping up\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(right_x, middle_y).waitAction(2000).moveTo(left_x, middle_y).release().perform();
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }


}
