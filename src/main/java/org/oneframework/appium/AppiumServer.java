package org.oneframework.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.oneframework.utils.CommandLineExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.oneframework.logger.LoggingManager.logMessage;

public class AppiumServer {

    public static AtomicInteger c = new AtomicInteger(4723);
    private static ThreadLocal<AppiumDriverLocalService> driverAppiumServer= new ThreadLocal<>();

    private static ThreadLocal<Integer> portAtomic= new ThreadLocal<>();

    public static void start() throws IOException {
        int port = c.getAndAdd(1);
        portAtomic.set(port);
        CommandLineExecutor.killProcessListeningAtPort(port);
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File("/Users/anil-patidar/.nvm/versions/node/v16.15.1/lib/node_modules/appium/build/lib/main.js"))
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        driverAppiumServer.set(builder.build());
        driverAppiumServer.get().start();
        logMessage("Appium server has been started");
    }

    public static void stop() throws IOException {
        driverAppiumServer.get().stop();
        logMessage("Appium server has been stopped");
    }

    public static AppiumDriverLocalService getAppiumDriverLocalService(){
       return driverAppiumServer.get();
    }

    public static int getPortNumber(){
        return portAtomic.get();
    }
}
