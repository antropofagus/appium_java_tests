package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
            SEARCH_INPUT = "org.wikipedia:id/search_src_text",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text, '{SUBSTRING}')]",
            SEARCH_CANCEL_BTN = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULTS_CONTAINER = "org.wikipedia:id/fragment_search_results",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_RESULT_LIST = "org.wikipedia:id/search_results_list",
            SEARCH_ARTICLE_TITLE = "org.wikipedia:id/page_list_item_title";


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATE METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATE METHODS*/

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.id(SEARCH_INIT_ELEMENT),
                "Can`t find 'Search Wikipedia' input",
                5);
        this.waitForElementPresent(
                By.id(SEARCH_INPUT),
                "Can`t find search input",
                5);
    }

    public void typeSearchString(String search_string) {
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT),
                "Can`t find and type into search input",
                search_string,
                5 );
    }

    public void waitForSearchResult(String search_string) {
        String search_result_xpath = getResultSearchElement(search_string);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Can`t find search result with substring " + search_string,
                15);
    }

    public  void  waitForSearchResultDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_RESULTS_CONTAINER),
                "search results are still present",
                5);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Can`t find and click search result with substring " + substring,
                15);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BTN),
                "Can`t find and click 'x' button",
                5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BTN),
                "'x' button is still present",
                5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BTN),
                "Can`t find 'x' button",
                5);
    }

    public void assertSearchResultIsNotPresent() {
        this.assertElementNotPresent(
                By.id(SEARCH_RESULT_ELEMENT),
                "Search results are still present");
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Can`t find anything by request",
                15);
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));

    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "can`t find empty result element",
                15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results");
    }

    public boolean assertSearchResultsContainsSearchString(List<WebElement> search_results, String search_substring) {
        for(WebElement element : search_results) {
            if(!element.getText().contains(search_substring))
                return false;
            }
        return true;
    }

    public List<WebElement> getSearchResults(){
        return driver.findElements(By.id(SEARCH_RESULT_ELEMENT));
    }
}
