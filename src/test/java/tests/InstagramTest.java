package tests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.checkerframework.checker.units.qual.A;
import org.oneframework.allureReport.TestListener;
import org.oneframework.config.AndroidDeviceModel;
import org.oneframework.config.DeviceConfig;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.logger.LoggingManager;
import org.oneframework.pageObjects.HomePage;
import org.oneframework.pageObjects.InstagramLoginPage;
import org.oneframework.utils.ADBUtilities;
import org.oneframework.utils.FileUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.List;

@Listeners({TestListener.class})
public class InstagramTest extends BaseTest {

    private static final LoggingManager log = new LoggingManager(InstagramTest.class.getName());
    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Instagram login", enabled = true)
    public void instalogin() throws Exception {
        // Assert.assertTrue(false);
        File screenShotFolder = new File(System.getProperty("user.dir")+ FileUtility.screenshotFilePath);
//        FileUtility.pushMultipleFiles(screenShotFolder);
//        FileUtility.pushMultipleFiles(screenShotFolder);
//        Thread.sleep(60000);
        FileUtility.pushFile(screenShotFolder);
        FileUtility.pushFile(screenShotFolder);
        Thread.sleep(20000);
        log.debug("Running instalogin");
        AndroidDriver<AndroidElement>androidDriver = (AndroidDriver<AndroidElement>) driverThread.get();
        androidDriver.closeApp();
        androidDriver.startActivity(new Activity("com.sec.android.app.myfiles","com.sec.android.app.myfiles.external.ui.MainActivity"));
        androidDriver.findElement(MobileBy.AccessibilityId("Search")).click();
        String search = "new UiSelector().text(\"Search\")";
        androidDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator(search)).sendKeys("Screenshot_X");
        String item = "new UiSelector().text(\"1 item\")";
        androidDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator(item)).click();
        String listItem = "new UiSelector().resourceId(\"com.sec.android.app.myfiles:id/file_detail_list_item\")";
        TouchAction touchAction = new TouchAction(androidDriver);
        LongPressOptions longPressOptions = LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(androidDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator(listItem))))
                .withDuration(Duration.ofSeconds(2));
        touchAction.longPress(longPressOptions).release().perform();

        String share = "new UiSelector().text(\"Share\")";
        androidDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator(share)).click();

        String feed = "new UiSelector().text(\"Feed\")";
        androidDriver.findElement(MobileBy.ByAndroidUIAutomator.AndroidUIAutomator(feed)).click();

        androidDriver.findElement(MobileBy.AccessibilityId("Next")).click();
        androidDriver.findElement(MobileBy.AccessibilityId("Next")).click();


//        androidDriver.closeApp();
//        androidDriver.launchApp();

        InstagramLoginPage instaLogin = new InstagramLoginPage(driverThread.get());
//        instaLogin.clickCamera();
//        Thread.sleep(3000);
//        instaLogin.clickMultipleSelect();
//        instaLogin.selectMultiplePhotos();
//        Thread.sleep(1000);
//        instaLogin.clickFirstNextBtn();
//        Thread.sleep(1000);
//        instaLogin.clickFirstNextBtn();
//        Thread.sleep(1000);
        instaLogin.writeText();
        Thread.sleep(10000);
        androidDriver.findElement(MobileBy.AccessibilityId("Share")).click();
        Thread.sleep(10000);


//        instaLogin.clickShareBtn();
        softAssert.assertTrue(true);
        softAssert.assertAll();
    }





}


