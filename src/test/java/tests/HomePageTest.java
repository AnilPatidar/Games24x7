package tests;

import org.oneframework.allureReport.TestListener;
import org.testng.Assert;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.logger.LoggerUtil;
import org.oneframework.logger.LoggingManager;
import org.oneframework.pageObjects.HomePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners({TestListener.class})
public class HomePageTest extends BaseTest {

   private static final LoggingManager log = new LoggingManager(HomePageTest.class.getName());

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "testing the signin page visually")
    public void testSignInOption() throws Exception {
       // Assert.assertTrue(false);
        log.debug("Running testSignInOption");
        System.out.println("Running testSignInOption");
        HomePage homePage = new HomePage(driverThread.get());
       softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("homePage"), "homePage baseline image isn't matching with actual image.");
       homePage.chooseSignInOption();
       softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("signinPage"), "signinPage baseline image isn't matching with actual image.");
       softAssert.assertAll();
    }

    @Test(description = "testing the signup page visually")
    public void testSignUpOption() throws Exception {
        log.debug("Running testSignUpOption");

        // Assert.assertTrue(false);
        System.out.println("Running testSignUpOption");
       HomePage homePage = new HomePage(driverThread.get());
        homePage.chooseSignUpOption();
       softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("signupPage"), "signupPage baseline image isn't matching with actual image.");
      softAssert.assertAll();
    }

}