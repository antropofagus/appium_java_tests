package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static String
            ARTICLE_TITLE = "org.wikipedia:id/view_page_title_text",
            VIEW_ARTICLE_IN_BROWSER_LINK = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_READING_LIST = "//*[@text='Add to reading list']",
            ADD_ARTICLE_TO_READING_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            READING_LIST_OK_BUTTON = "//*[@text='OK']",
            READING_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTING_READING_LIST_ELEMENT_TPL = "//*[@text='{FOLDER}']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForArticleTitle() {
        return this.waitForElementPresent(
                By.id(ARTICLE_TITLE),
                "cant find article title element",
                20);
    }

    public String getArticleTitle() {
        WebElement article_title = this.waitForArticleTitle();
        return article_title.getAttribute("text");
    }

    private static String getSavedReadingListElement(String folder_name) {
        return EXISTING_READING_LIST_ELEMENT_TPL.replace("{FOLDER}", folder_name);
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                By.xpath(VIEW_ARTICLE_IN_BROWSER_LINK),
                "can`t find the end of article",
                20);
    }

    public void addArticleToNewReadingList(String folder_name) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "can`t find article options element",
                5);

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_READING_LIST),
                "can`t find option to add article to reading list",
                15);

        this.waitForElementAndClick(
                By.id(ADD_ARTICLE_TO_READING_LIST_OVERLAY),
                "Can`t find 'got it' tip overlay",
                15);

        this.waitForElementAndClear(
                By.id(READING_LIST_NAME_INPUT),
                "can`t find input to set reading list name",
                15);

        this.waitForElementAndSendKeys(
                By.id(READING_LIST_NAME_INPUT),
                "can`t find and set input and set reading list name",
                folder_name,
                5);

        this.waitForElementAndClick(
                By.xpath(READING_LIST_OK_BUTTON),
                "can`t find OK button",
                5);
    }

    public void addArticleToExistingReadingList(String folder_name) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "can`t find article options element",
                5);

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_READING_LIST),
                "can`t find option to add article to reading list",
                15);

        this.waitForElementAndClick(
                By.xpath(getSavedReadingListElement(folder_name)),
                    "Can`t find folder in MyList with name: " + folder_name,
                    15);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Can`t close article, can`t find X button",
                5);
    }

    public boolean assertArticleTitlePresent() {
        return this.assertElementPresent(
                By.id(ARTICLE_TITLE),
                "article title is not present on page");
    }
}
