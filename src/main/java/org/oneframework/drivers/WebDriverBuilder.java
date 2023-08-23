package org.oneframework.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.oneframework.config.DeviceConfig;
import org.oneframework.enums.PlatformName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class WebDriverBuilder extends DeviceConfig {

    WebDriver driver;

    public WebDriver setupDriver(String platformName) {
        if (platformName.equalsIgnoreCase(PlatformName.CHROME.name())) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", "/Users/jitu-patel/github/Games24x7/driver/chromedriver");
            driver = new ChromeDriver(chromeOptions);
        } else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.name())) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        setExecutionPlatform(platformName);
        return driver;
    }

}
