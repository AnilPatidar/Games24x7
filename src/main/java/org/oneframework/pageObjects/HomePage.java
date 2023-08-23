package org.oneframework.pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.oneframework.helpers.Page;
import org.oneframework.imageCompare.ImageComparator;
import org.oneframework.logger.LoggingManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends Page {
    private static final LoggingManager log = new LoggingManager(HomePage.class.getName());
    WebDriver driver;

    @FindBy(xpath = "//a[@title='Log In'][1]")
    @AndroidFindBy(id = "login_button")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[contains(@label, 'Log In')]")
    private WebElement eleSignInBtn;

    @FindBy(xpath = "//a[@title='Get Started']")
    @AndroidFindBy(id = "create_site_button")
    @iOSXCUITFindBy(id = "Sign up for WordPress.com Button")
    private WebElement eleSignUpBtn;



    @FindBy(xpath = "//a[@data-testid='SideNav_NewTweet_Button']")
    @AndroidFindBy(id = "create_site_button")
    @iOSXCUITFindBy(id = "Sign up for WordPress.com Button")
    private WebElement postButton;

    public HomePage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Initializing the "+this.getClass().getSimpleName()+" elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }


    public void postTweet(String text){
        clickElement(postButton);


    }



}