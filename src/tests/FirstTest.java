package tests;

import lib.CoreTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import ui.ArticlePageObject;
import ui.NavigationUI;
import ui.ReadingListsPageObject;
import ui.SearchPageObject;

import java.util.List;

public class FirstTest extends CoreTestCase {

    @Test
    public void testSearch() {
        String search_string = "Java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
    }

    @Test
    public void testSearchCancel() {
        String search_string = "Java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertSearchResultIsNotPresent();
    }

    @Test
    public void testSearchResultsAreRelevant() {
        String search_string = "Java";
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
        List<WebElement> search_results = SearchPageObject.getSearchResults();
        SearchPageObject.assertSearchResultsContainsSearchString(search_results, search_string);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        String search_string = "Java";
        String folder_name = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForArticleTitle();
        String first_article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToNewReadingList(folder_name);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
        SearchPageObject.clickByArticleWithSubstring("JavaScript");

        ArticlePageObject.waitForArticleTitle();
        String second_article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToExistingReadingList(folder_name);
        ArticlePageObject.closeArticle();

        NavigationUI Navigation = new NavigationUI(driver);
        Navigation.openReadingLists();

        ReadingListsPageObject MyReadingList = new ReadingListsPageObject(driver);
        MyReadingList.openFolderByName(folder_name);
        MyReadingList.waitForArticleToAppearByTitle(first_article_title);
        MyReadingList.waitForArticleToAppearByTitle(second_article_title);
        Assert.assertEquals(
                "not expected amount of saved articles",
                2,
                MyReadingList.getAmountOfSavedArticles()
        );
        MyReadingList.swipeByArticleToDelete(first_article_title);
        MyReadingList.openArticleFromReadingList(second_article_title);

        ArticlePageObject.waitForArticleTitle();

        Assert.assertEquals("Article titles do not match",
                second_article_title,
                ArticlePageObject.getArticleTitle());
    }

    @Test
    public void testArticleTitleIsPresent() {
        String search_string = "Java";
        String folder_name = "Learning programming";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchString(search_string);
        SearchPageObject.waitForSearchResult(search_string);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertArticleTitlePresent();
    }


}
