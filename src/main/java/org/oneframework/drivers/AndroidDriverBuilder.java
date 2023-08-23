package org.oneframework.drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.oneframework.appium.AppiumServer;
import org.oneframework.config.AndroidDeviceModel;
import org.oneframework.config.DeviceConfig;
import org.oneframework.logger.LoggingManager;
import org.oneframework.utils.FileUtility;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidDriverBuilder extends DeviceConfig {

    private static final LoggingManager log = new LoggingManager(AndroidDriverBuilder.class.getName());
    AndroidDriver driver;

    public AndroidDriver setupDriver(String model) throws IOException {
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        AndroidDeviceModel device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
        log.info("Received the " + model + " device configuration for execution");
        setExecutionPlatform(model);
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device.getDeviceName());
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, device.getPlatformName());
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getPlatformVersion());
        androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, device.getAutomationName());
        androidCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        androidCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        androidCapabilities.setCapability("autoGrantPermissions", true);

        // androidCapabilities.setCapability(MobileCapabilityType.APP, FileUtility.getFile(device.getApp()).getAbsolutePath());
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, device.getPackageName());
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, device.getActivity());
        driver = new AndroidDriver(new URL("http://127.0.0.1:"+ AppiumServer.getPortNumber() +"/wd/hub"), androidCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        log.info("Android driver has been created for the " + model + " device");
        return driver;
    }
}
