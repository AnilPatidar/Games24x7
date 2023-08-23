package tests;

import org.oneframework.allureReport.TestListener;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.logger.LoggingManager;
import org.oneframework.pageObjects.HomePage;
import org.oneframework.pageObjects.PostPage;
import org.oneframework.pageObjects.SignUpPage;
import org.oneframework.utils.PostUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.swing.text.Utilities;

@Listeners({TestListener.class})
public class TwitterPageTest extends BaseTest {

    private static final LoggingManager log = new LoggingManager(TwitterPageTest.class.getName());

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "testing the signin page visually")
    public void testSignInOption() throws Exception {
        // Assert.assertTrue(false);
        log.debug("Running testSignInOption");
        System.out.println("Running testSignInOption");
        SignUpPage signUpPage = new SignUpPage(driverThread.get());
        signUpPage.login("gnana.don@gmail.com","@arkanto15810725","9443456395");
        PostPage postPage = new PostPage(driverThread.get());
        postPage.postTweet(PostUtils.post1);

        postPage.postTweet(PostUtils.post2);
        postPage.postTweet(PostUtils.post3);


    }


}

