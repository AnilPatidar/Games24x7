package org.oneframework.pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.oneframework.helpers.Page;
import org.oneframework.logger.LoggingManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class PostPage extends Page {

    private static final LoggingManager log = new LoggingManager(PostPage.class.getName());
    @FindBy(xpath = "//a[@data-testid='SideNav_NewTweet_Button']")
    @AndroidFindBy(id = "label")
    @iOSXCUITFindBy(id = "Log in to WordPress.com using an email address to manage all your WordPress sites.")
    private WebElement postElement;


    @FindBy(xpath = "//a[@data-testid='SideNav_NewTweet_Button']")
    private WebElement postFinalButton;

    @FindBy(xpath = "//div[@class='public-DraftStyleDefault-block public-DraftStyleDefault-ltr' and @data-offset-key='82on5-0-0']")
    private WebElement textArea;



    WebDriver driver;

    public PostPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Initializing the " + this.getClass().getSimpleName() + " elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }

    public void postTweet(String tweet) throws Exception {
        clickElement(postElement);
        clickElement(textArea);
        enterText(textArea,tweet);
        List<WebElement> s = (List<WebElement>) driver.findElements(By.xpath("//a[@data-testid='SideNav_NewTweet_Button']"));
        clickElement(postFinalButton);
        Thread.sleep(40000);


    }



}
