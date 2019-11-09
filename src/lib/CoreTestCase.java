package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("app", "/Users/elcentipede/IdeaProjects/appium_java_test/apks/org.wikipedia.apk");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("appPackage", "org.wikipedia");

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }
}
