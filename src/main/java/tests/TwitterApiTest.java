package tests;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.json.JSONObject;
import org.oneframework.clients.BaseRestClient;
import org.oneframework.config.ConfigUtils;
import org.oneframework.logger.LoggingManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TwitterApiTest extends BaseRestClient {

    LoggingManager log = new LoggingManager(TwitterApiTest.class.getName());
    @Test
    public void postTweetApi() {
        //test data
        String myTweet = "test data again";
        JSONObject myTweetObject = new JSONObject();
        myTweetObject.put("text",myTweet);

        FilterableRequestSpecification request = getRequestSpecification();

        //access tokens & key
        String consumerKey = ConfigUtils.getConfig("consumerkey");
        String consumerSecret = ConfigUtils.getConfig("consumersecret");
        String accessToken = ConfigUtils.getConfig("accesstoken");
        String secretToken = ConfigUtils.getConfig("secrettoken");

        //add key & token in oauth
        request.auth().oauth(consumerKey, consumerSecret, accessToken, secretToken);

        //add request body
        request.body(myTweetObject.toString());

        //post api call for tweet
        Response response = request.post("https://api.twitter.com/2/tweets");

        //assert response code
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
