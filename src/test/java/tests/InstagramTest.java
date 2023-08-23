package tests;

import org.oneframework.allureReport.TestListener;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.logger.LoggingManager;
import org.oneframework.pageObjects.HomePage;
import org.oneframework.pageObjects.InstagramLoginPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners({TestListener.class})
public class InstagramTest extends BaseTest {

    private static final LoggingManager log = new LoggingManager(InstagramTest.class.getName());
    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Instagram login", enabled = true)
    public void instalogin() throws Exception {
        // Assert.assertTrue(false);
        log.debug("Running instalogin");
        InstagramLoginPage instaLogin = new InstagramLoginPage(driverThread.get());
        instaLogin.clickCamera();
        Thread.sleep(3000);
        instaLogin.clickMultipleSelect();
        instaLogin.selectMultiplePhotos();
        Thread.sleep(1000);
        instaLogin.clickFirstNextBtn();
        Thread.sleep(1000);
        instaLogin.clickFirstNextBtn();
        Thread.sleep(1000);
        instaLogin.writeText();
        Thread.sleep(1000);
        instaLogin.clickShareBtn();
        softAssert.assertTrue(true);
        softAssert.assertAll();
    }


}


