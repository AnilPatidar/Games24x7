package tests;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.json.JSONObject;
import org.oneframework.clients.BaseRestClient;
import org.oneframework.config.ConfigUtils;
import org.oneframework.logger.LoggingManager;
import org.oneframework.utils.PostUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TwitterApiTest extends BaseRestClient {

    LoggingManager log = new LoggingManager(TwitterApiTest.class.getName());


    @DataProvider(parallel = false)
    public Object[][] getData(){
        return new Object[][]{
                {PostUtils.post4},
                {PostUtils.post5},
        };
    }

    @Test(description = "Posting in twitter using api",dataProvider = "getData")
    public void postTweetApi(String postMessage) {
        //test data
        long timeStamp = System.currentTimeMillis();
        String myTweet = String.format(postMessage,timeStamp);
        JSONObject myTweetObject = new JSONObject();
        myTweetObject.put("text",myTweet);

        FilterableRequestSpecification request = getRequestSpecification();

        //access tokens & key
        log.info("Adding authentication token");
        String consumerKey = ConfigUtils.getConfig("consumerkey");
        String consumerSecret = ConfigUtils.getConfig("consumersecret");
        String accessToken = ConfigUtils.getConfig("accesstoken");
        String secretToken = ConfigUtils.getConfig("secrettoken");

        //add key & token in oauth
        request.auth().oauth(consumerKey, consumerSecret, accessToken, secretToken);

        log.info("Adding request body");
        //add request body
        request.body(myTweetObject.toString());

        //post api call for tweet
        log.info("Calling twitter post api");
        log.info("Request Body ==> " + request.getBody().toString());
        Response response = request.post("https://api.twitter.com/2/tweets");
        log.info("Response Body ==> " + response.getBody().toString());
        //assert response code
        log.info("Validating respons code");
        Assert.assertEquals(response.getStatusCode(), 201, "Response code is not 201");
    }
}
