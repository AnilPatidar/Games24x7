package org.oneframework.pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.oneframework.helpers.Page;
import org.oneframework.logger.LoggingManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignInPage extends Page {
    private static final LoggingManager log = new LoggingManager(SignInPage.class.getName());

    @FindBy(xpath = "//div[@class='login__form-header']")
    @AndroidFindBy(id = "label")
    @iOSXCUITFindBy(id = "Log in to WordPress.com using an email address to manage all your WordPress sites.")
    private WebElement elePageTitle;

    @FindBy(xpath = "//div[contains(text(), 'Log in to')]")
    @AndroidFindBy(id = "label")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@label, 'Log in to WordPress.com using an email address to manage all your WordPress sites.')]")
    private WebElement eleSignInTitle;


    WebDriver driver;

    public SignInPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Initializing the " + this.getClass().getSimpleName() + " elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }

    public String getTitle() throws Exception {
        return getText(elePageTitle);
    }

    public void clickOnSignInTitle() throws Exception {
        clickElement(eleSignInTitle);
    }
}
