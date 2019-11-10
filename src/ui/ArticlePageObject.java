package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
    private static String
            ARTICLE_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_title_text']",
            VIEW_ARTICLE_IN_BROWSER_LINK = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_READING_LIST = "xpath://*[@text='Add to reading list']",
            ADD_ARTICLE_TO_READING_LIST_OVERLAY = "xpath://*[@resource-id='org.wikipedia:id/onboarding_button']",
            READING_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            READING_LIST_NAME_INPUT = "xpath://*[@resource-id='org.wikipedia:id/text_input']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            EXISTING_READING_LIST_ELEMENT_TPL = "xpath://*[@text='{FOLDER}']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForArticleTitle() {
        return this.waitForElementPresent(
                ARTICLE_TITLE,
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
                VIEW_ARTICLE_IN_BROWSER_LINK,
                "can`t find the end of article",
                20);
    }

    public void addArticleToNewReadingList(String folder_name) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "can`t find article options element",
                5);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_READING_LIST,
                "can`t find option to add article to reading list",
                15);

        this.waitForElementAndClick(
                ADD_ARTICLE_TO_READING_LIST_OVERLAY,
                "Can`t find 'got it' tip overlay",
                15);

        this.waitForElementAndClear(
                READING_LIST_NAME_INPUT,
                "can`t find input to set reading list name",
                15);

        this.waitForElementAndSendKeys(
                READING_LIST_NAME_INPUT,
                "can`t find and set input and set reading list name",
                folder_name,
                5);

        this.waitForElementAndClick(
                READING_LIST_OK_BUTTON,
                "can`t find OK button",
                5);
    }

    public void addArticleToExistingReadingList(String folder_name) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "can`t find article options element",
                5);

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_READING_LIST,
                "can`t find option to add article to reading list",
                15);

        this.waitForElementAndClick(
                getSavedReadingListElement(folder_name),
                    "Can`t find folder in MyList with name: " + folder_name,
                    15);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Can`t close article, can`t find X button",
                5);
    }

    public boolean assertArticleTitlePresent() {
        return this.assertElementPresent(
                ARTICLE_TITLE,
                "article title is not present on page");
    }
}
