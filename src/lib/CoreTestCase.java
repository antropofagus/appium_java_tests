package lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    public static final String
        PLATFORM_IOS = "ios",
        PLATFORM_ABDROID = "android";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        driver = getDriver(); //new AndroidDriver(new URL(AppiumURL), capabilities);
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    private String getPlatformEnv() {
        return System.getenv("PLATFORM");
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv(String platform) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (platform.equals(PLATFORM_ABDROID)) {
            capabilities.setCapability("platformVersion", "9");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("app", "/Users/elcentipede/IdeaProjects/appium_java_test/apks/org.wikipedia.apk");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("appPackage", "org.wikipedia");
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformVersion", "11.2");
            capabilities.setCapability("platformName", "ios");
            capabilities.setCapability("deviceName", "iPhone X");
            capabilities.setCapability("app", "/Users/elcentipede/IdeaProjects/appium_java_test/apks/Wikipedia.app");
        } else {
            throw new Exception("can`t get run platform from env variable. Platform value: " + platform);
        }
        return capabilities;
    }


    private AppiumDriver getDriver() throws Exception {
        if (this.getPlatformEnv().equals(PLATFORM_ABDROID)) {
            return new AndroidDriver(new URL(AppiumURL), getCapabilitiesByPlatformEnv(getPlatformEnv()));
        } else if (this.getPlatformEnv().equals(PLATFORM_IOS)) {
            return new IOSDriver(new URL(AppiumURL), getCapabilitiesByPlatformEnv(getPlatformEnv()));
        } else {
            throw new Exception("Can`t detect type of the Driver. Platform value: " + getPlatformEnv());
        }
    }


}
