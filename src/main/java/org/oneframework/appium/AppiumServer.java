package org.oneframework.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.oneframework.logger.LoggingManager;
import org.oneframework.utils.CommandLineExecutor;
import org.oneframework.utils.ReadSymbolicLinksUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class AppiumServer {

    private static final LoggingManager log = new LoggingManager(AppiumServer.class.getName());

    public static AtomicInteger c = new AtomicInteger(4723);
    private static ThreadLocal<AppiumDriverLocalService> driverAppiumServer= new ThreadLocal<>();

    private static ThreadLocal<Integer> portAtomic= new ThreadLocal<>();

    public static void start() throws IOException {
        int port = c.getAndAdd(1);
        portAtomic.set(port);
        CommandLineExecutor.killProcessListeningAtPort(port);
        String appiumPath=new ReadSymbolicLinksUtil().getRealPathOfAppium();
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumPath))
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        driverAppiumServer.set(builder.build());
        driverAppiumServer.get().start();
        log.info("Appium server has been started");
    }

    public static void stop() throws IOException {
        driverAppiumServer.get().stop();
        log.info("Appium server has been stopped");
    }

    public static AppiumDriverLocalService getAppiumDriverLocalService(){
       return driverAppiumServer.get();
    }

    public static int getPortNumber(){
        return portAtomic.get();
    }
}
