package org.oneframework.pageObjects;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.oneframework.helpers.Page;
import org.oneframework.logger.LoggingManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SearchPage extends Page {

    private static final LoggingManager log = new LoggingManager(SearchPage.class.getName());
    @FindBy(xpath ="//input[@data-testid='SearchBox_Search_Input']")
    private WebElement search;


    @FindBy(css ="article[data-testid='tweet']")
    private WebElement searchResult;

    WebDriver driver;

    public SearchPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Initializing the " + this.getClass().getSimpleName() + " elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }

    public WebElement search(String postToSearch){
        clickElement(search);
        enterText(search,postToSearch);
        search.sendKeys(Keys.RETURN);
        return searchResult;
    }

}
