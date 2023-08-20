package tests;

import org.oneframework.appium.AppiumServer;
import org.oneframework.drivers.AndroidDriverBuilder;
import org.oneframework.drivers.IOSDriverBuilder;
import org.oneframework.drivers.WebDriverBuilder;
import org.oneframework.enums.PlatformName;
import org.oneframework.enums.PlatformType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;

import static org.oneframework.logger.LoggingManager.logMessage;

public class BaseTest {


    public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    @Parameters({"platformType", "platformName"})
    @BeforeTest
    public void startAppiumServer(String platformType, @Optional String platformName) throws IOException {
        if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {

        }
    }

    @Parameters({"platformType", "platformName"})
    @AfterMethod
    public void stopAppiumServer(String platformType, @Optional String platformName) throws IOException {
        if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {
                if (AppiumServer.getAppiumDriverLocalService() != null || AppiumServer.getAppiumDriverLocalService().isRunning()) {
                logMessage("Appium server has been stopped");
            }
        }
    }

    @Parameters({"platformType", "platformName", "model"})
    @BeforeMethod
    public void setupDriver(String platformType, String platformName, @Optional String model) throws IOException {
        if (platformType.equalsIgnoreCase(PlatformType.WEB.toString())) {
            driverThread.set(setupWebDriver(platformName));
            driverThread.get().get("https://www.wordpress.com");
        } else if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {
            AppiumServer.start();
            driverThread.set(setupMobileDriver(platformName, model));
        }
    }

    public WebDriver setupMobileDriver(String platformName, String model) throws IOException {
        if (platformName.equalsIgnoreCase(PlatformName.ANDROID.toString())) {
            return new AndroidDriverBuilder().setupDriver(model);
        } else if (platformName.equalsIgnoreCase(PlatformName.IOS.toString())) {
            return new IOSDriverBuilder().setupDriver(model);
        }
        logMessage(model + " driver not has been created for execution");
        return null;
    }

    public WebDriver setupWebDriver(String platformName) {
        if (platformName.equalsIgnoreCase(PlatformName.CHROME.toString())) {
            return new WebDriverBuilder().setupDriver(platformName);
        } else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.toString())) {
            return new WebDriverBuilder().setupDriver(platformName);
        }
        logMessage(platformName + " driver has not been created for execution");
        return null;
    }

    @AfterMethod
    public void teardownDriver() throws IOException {
        driverThread.get().quit();
        logMessage("Driver has been quit from execution");
    }



    private void killExistingAppiumProcess() throws IOException {
        Runtime.getRuntime().exec("killall node");
        logMessage("Killing existing appium process");
    }

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {

        }
        return driverThread.get();
    }


}