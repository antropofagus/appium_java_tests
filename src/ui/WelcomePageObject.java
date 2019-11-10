package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {
    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public static final String
        LEARN_MORE_LINK = "id:Learn more about Wikipedia",
        NEXT_BUTTON = "id:Next",
        ADD_OR_EDIT_PREFERRED_LANG_TEXT = "id:Add or edit preferred languages",
        LEARN_MORE_ABOUT_DATA_COLLECTED = "id:Learn more about data collected",
        NEW_WAY_TO_EXPLORE = "id:New ways to explore",
        GET_STARTED_BUTTON = "id:Get started",
        SKIP_LINK = "id:Skip";

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                LEARN_MORE_LINK,
                "Can`t find '" + LEARN_MORE_LINK + "' link",
                15);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(
                NEW_WAY_TO_EXPLORE,
                "Can`t find '" + NEW_WAY_TO_EXPLORE + "' link",
                10);
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(
                ADD_OR_EDIT_PREFERRED_LANG_TEXT,
                "Can`t find '" + ADD_OR_EDIT_PREFERRED_LANG_TEXT + "' link",
                10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(
                LEARN_MORE_ABOUT_DATA_COLLECTED,
                "Can`t find '" + LEARN_MORE_ABOUT_DATA_COLLECTED + "' link",
                10);
    }


    public void clickNextButton() {
        this.waitForElementAndClick(
                NEXT_BUTTON,
                "Can`t find and click '" + NEXT_BUTTON + "' button",
                10);
    }

    public void clicGetStartedButton() {
        this.waitForElementAndClick(
                GET_STARTED_BUTTON,
                "Can`t find and click '" + GET_STARTED_BUTTON + "' button",
                10);
    }
}
