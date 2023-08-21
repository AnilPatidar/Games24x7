package tests;

import org.oneframework.allureReport.TestListener;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.pageObjects.HomePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;

@Listeners({TestListener.class})
public class HomePageTest extends BaseTest {

   private static Logger LogManager;
    private static final Logger LOGGER = LogManager.getLogger(String.valueOf(HomePageTest.class));

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "testing the signin page visually")
    public void testSignInOption() throws Exception {
        LOGGER.info("Running testSignInOption");
        System.out.println("Running testSignInOption");
        HomePage homePage = new HomePage(driverThread.get());
        softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("homePage"), "homePage baseline image isn't matching with actual image.");
        homePage.chooseSignInOption();
        softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("signinPage"), "signinPage baseline image isn't matching with actual image.");
        softAssert.assertAll();
    }

    @Test(description = "testing the signup page visually")
    public void testSignUpOption() throws Exception {
        System.out.println("Running testSignUpOption");
        HomePage homePage = new HomePage(driverThread.get());
        homePage.chooseSignUpOption();
        softAssert.assertTrue(new ImageComparator(driverThread.get()).compare("signupPage"), "signupPage baseline image isn't matching with actual image.");
        softAssert.assertAll();
    }

}