package org.oneframework.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Page {

    WebDriver driver;

    public void clickElement(WebElement element) {
        element.click();
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public void enterText(WebElement element, String value) {
        element.sendKeys(value);
    }

    public void clearTest (WebElement element) {
        element.clear();
    }

}
