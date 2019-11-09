package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ReadingListsPageObject extends MainPageObject{
    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{NAME}']",
        READING_LIST_ITEM = "org.wikipedia:id/page_list_item_container",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{ARTICLE_TITLE}']";

    /*TEMPLATE METHODS*/
    private static String getFolderXpathByName(String folder_name) {
        return FOLDER_BY_NAME_TPL.replace("{NAME}", folder_name);
    }

    private static String getArticleXpathByName(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", article_title);
    }
    /*TEMPLATE METHODS*/

    public ReadingListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String folder_name) {
        String folder_name_xpath = getFolderXpathByName(folder_name);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cant`t find created folder by name "+ folder_name,
                5);
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getArticleXpathByName(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Can`t find saved article by title " + article_title,
                15);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getArticleXpathByName(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title " + article_title,
                15);
    }

    public int getAmountOfSavedArticles() {
        return this.getAmountOfElements(By.id(READING_LIST_ITEM));
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getArticleXpathByName(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Can`t find saved article with title "+ article_title);
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void openArticleFromReadingList(String article_title) {
        String article_xpath = getArticleXpathByName(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Can`t find and click article by title " + article_title,
                15);
    }
}
