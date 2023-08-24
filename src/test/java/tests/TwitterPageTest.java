package tests;

import org.oneframework.config.ConfigUtils;
import org.oneframework.logger.LoggingManager;
import org.oneframework.pageObjects.PostPage;
import org.oneframework.pageObjects.SearchPage;
import org.oneframework.pageObjects.SignUpPage;
import org.oneframework.utils.FileUtility;
import org.oneframework.utils.PostUtils;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TwitterPageTest extends BaseTest {

    private static final LoggingManager log = new LoggingManager(TwitterPageTest.class.getName());

    SoftAssert softAssert = new SoftAssert();

    @Test(description = "Posting in twitter",dataProvider = "getData",priority = 1)
    public void testSignInOption(String post,int index) throws Exception {
        long timeStamp = System.currentTimeMillis();
        SignUpPage signUpPage = new SignUpPage(driverThread.get());
        String email = ConfigUtils.getConfig("email");
        String userName = ConfigUtils.getConfig("username");
        String password = ConfigUtils.getConfig("password");
        signUpPage.login(email,userName,password);
        if(index<=3){
            log.debug("Posting content in twitter in UI");
            System.out.println("Posting content in twitter in UI");
            PostPage postPage = new PostPage(driverThread.get());
            System.out.println(String.format(post,timeStamp));
            postPage.postTweet(String.format(post,timeStamp));
        }else {
            //Add code for posting via API
        }
        //search tweet
        SearchPage searchPage = new SearchPage(driverThread.get());
        WebElement searchResult = searchPage.search(String.format(post,timeStamp));
        FileUtility.takeScreenShot(searchResult);

    }

    @DataProvider(parallel = false)
    public Object[][] getData(){
        return new Object[][]{
                {PostUtils.post1,1},
//                {PostUtils.post2,2},
//                {PostUtils.post3,3},
//                {PostUtils.post4,4},
//                {PostUtils.post5,5},
        };
    }


}

