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


public class SignUpPage extends Page {

    private static final LoggingManager log = new LoggingManager(SignUpPage.class.getName());
    @FindBy(xpath = "//div[@class='login__form-header']")
    @AndroidFindBy(id = "label")
    @iOSXCUITFindBy(id = "Log in to WordPress.com using an email address to manage all your WordPress sites.")
    private WebElement elePageTitle;

    @FindBy(xpath = "//a[@data-testid='loginButton']")
    private WebElement signInPage;

    @FindBy(xpath ="//input[@dir='auto']")
    private WebElement phoneNumber;

    @FindBy(xpath ="/span[text()='Next']")
    private WebElement nextButton;

    @FindBy(xpath ="//input[@name='password']")
    private WebElement passwordButton;


    private WebElement loginButton;


    WebDriver driver;

    public SignUpPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Initializing the " + this.getClass().getSimpleName() + " elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }

    public String getTitle() throws Exception {
        return getText(elePageTitle);
    }

    public void login(String email, String password){

        clickElement(phoneNumber);
        enterText(phoneNumber,email);
        clickElement(nextButton);
        enterText(passwordButton,password);
    }

}
