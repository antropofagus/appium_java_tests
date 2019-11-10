package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {
    private static final String
        READING_LISTS_BUTTON = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void openReadingLists() {
        this.waitForElementAndClick(
                READING_LISTS_BUTTON,
                "Can`t find navigation button to My lists",
                15);
    }
}
