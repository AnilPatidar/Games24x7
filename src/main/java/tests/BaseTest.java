package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.oneframework.appium.AppiumServer;
import org.oneframework.config.AndroidDeviceModel;
import org.oneframework.config.DeviceConfig;
import org.oneframework.drivers.AndroidDriverBuilder;
import org.oneframework.drivers.IOSDriverBuilder;
import org.oneframework.drivers.WebDriverBuilder;
import org.oneframework.enums.PlatformName;
import org.oneframework.enums.PlatformType;
import org.oneframework.logger.LoggingManager;
import org.oneframework.utils.ADBUtilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class BaseTest {

    private static final LoggingManager log = new LoggingManager(BaseTest.class.getName());
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
                log.info("Appium server has been stopped");
            }
        }
    }

    @Parameters({"platformType", "platformName", "model"})
    @BeforeMethod
    public void setupDriver(String platformType, String platformName, @Optional String model) throws IOException {
        model="pixel";
        if (platformType.equalsIgnoreCase(PlatformType.WEB.toString())) {
            driverThread.set(setupWebDriver(platformName));
            driverThread.get().get("https://twitter.com");
        } else if (platformType.equalsIgnoreCase(PlatformType.MOBILE.toString())) {
            AppiumServer.start();
            driverThread.set(setupMobileDriver(platformName, model));
        }
    }

    public WebDriver setupMobileDriver(String platformName, String model) throws IOException {
        model="pixel";
        if (platformName.equalsIgnoreCase(PlatformName.ANDROID.toString())) {
            return new AndroidDriverBuilder().setupDriver(model);
        } else if (platformName.equalsIgnoreCase(PlatformName.IOS.toString())) {
            return new IOSDriverBuilder().setupDriver(model);
        }
        log.info(model + " driver not has been created for execution");
        return null;
    }

    public WebDriver setupWebDriver(String platformName) {
        if (platformName.equalsIgnoreCase(PlatformName.CHROME.toString())) {
            return new WebDriverBuilder().setupDriver(platformName);
        } else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.toString())) {
            return new WebDriverBuilder().setupDriver(platformName);
        }
        log.info(platformName + " driver has not been created for execution");
        return null;
    }

    @AfterMethod
    public void teardownDriver(ITestResult result) throws IOException, InterruptedException {
        if(!result.isSuccess()) {
            if(System.getenv("platform").contains("android")){
                String testName = result.getMethod().getMethodName();
                AppiumDriver driver = (AppiumDriver) driverThread.get();
                AndroidDeviceModel device = DeviceConfig.readAndroidDeviceConfig().getAndroidDeviceByName("pixel");
                String deviceId = device.getDeviceName();
                String logFileName = System.currentTimeMillis()+"_"+deviceId+"_"+testName+".log";
                ADBUtilities.dumpAdbLogs(logFileName, (AndroidDriver<AndroidElement>) driver);
                log.info("Logs path:"+System.getProperty("user.dir")+ "/Logs/android/"+logFileName );
                //Screenshot
                String screenShotFileName = System.currentTimeMillis()+"_"+deviceId+"_"+testName+".png";
                File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
                ADBUtilities.dumpScreenShot(screenshotFile,screenShotFileName);
                log.info("Screenshot path:"+System.getProperty("user.dir")+ "/Screenshot/"+screenShotFileName );
                //Video
                String videoFileName = System.currentTimeMillis()+"_"+deviceId+"_"+testName+".mp4";
                ADBUtilities.dumpVideo((AndroidDriver<AndroidElement>) driver,videoFileName);
                log.info("Video path:"+System.getProperty("user.dir")+ "/Video/"+videoFileName );
            }
        }

        driverThread.get().quit();
        log.info("Driver has been quit from execution");
    }



    private void killExistingAppiumProcess() throws IOException {
        Runtime.getRuntime().exec("killall node");
        log.info("Killing existing appium process");
    }

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {

        }
        return driverThread.get();
    }


}