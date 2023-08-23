package org.oneframework.pageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.oneframework.helpers.Page;
import org.oneframework.logger.LoggingManager;
import org.oneframework.utils.PostUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InstagramLoginPage extends Page {

    private static final LoggingManager log = new LoggingManager(InstagramLoginPage.class.getName());

    WebDriver driver;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Username, email or mobile number']")
    private WebElement userName;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Password']")
    private WebElement userPassword;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Log in']")
    private WebElement loginBtn;

    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc='Camera']/android.widget.ImageView")
    private WebElement camera;

    //@AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Select multiple on']")
    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Select multiple']")
    private WebElement selectMultiple;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Next']")
    private WebElement finalNextButtton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next']")
    private WebElement firstNextButtton;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private List<WebElement> photoSelector;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Share']")
    private WebElement share;

    @AndroidFindBy(id = "com.instagram.android:id/caption_text_view")
    private WebElement writeCaption;

    public void writeText(){
        enterText(writeCaption, PostUtil.post4);
    }
    public void enterUserName(){
        enterText(userName,"himanis021@gmail.com");
    }

    public void enterPassword(){
        enterText(userPassword,"test@123");
    }

    public void clickLoginIn(){
        clickElement(loginBtn);
    }


    public void clickFirstNextBtn(){
        clickElement(firstNextButtton);
    }

    public void clickShareBtn(){
        clickElement(share);
    }


    public void clickFinalNextBtn(){
        clickElement(finalNextButtton);
    }



    public void clickCamera(){
        clickElement(camera);
    }

    public void clickMultipleSelect(){
        clickElement(selectMultiple);
    }

    public void selectMultiplePhotos() throws InterruptedException {
        //System.out.println("galary size "+photoSelector.size());
        int i=0;
        for(WebElement e:photoSelector){
            if(i==0){
                continue;
            }
            if(i==6){
                break;
            }
           // Thread.sleep(1000);
            clickElement(e);


        }

    }


    public InstagramLoginPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        log.info("Initializing the "+this.getClass().getSimpleName()+" elements");
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        Thread.sleep(1000);
    }

}
