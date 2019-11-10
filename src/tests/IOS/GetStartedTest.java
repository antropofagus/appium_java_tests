package tests.IOS;

import lib.CoreTestCase;
import org.junit.Test;
import ui.WelcomePageObject;

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testPassThroughWelcome() {
        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNextButton();
        WelcomePageObject.waitForAddOrEditPreferredLangText();
        WelcomePageObject.clickNextButton();
        WelcomePageObject.waitForLearnMoreAboutDataCollectedText();
        WelcomePageObject.clicGetStartedButton();
    }
}
