package org.oneframework.logger;

import com.epam.reportportal.service.ReportPortal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class LoggingManager implements ILogger {
    private final Logger logger ;

    public LoggingManager(String className) {
        logger = LogManager.getLogger(className);
    }




    public void debug(String message) {
        logger.debug(message);
        ReportPortal.emitLog(message, "DEBUG", new Date());
    }

    public void info(String message) {
        logger.info(message);
        try {
            ReportPortal.emitLog("RP_" + message, "INFO", new Date());
        }catch (Exception e){
            System.out.println("Report Portal Exception ---->"+e.getMessage());
        }
    }

    public void trace(String message) {
        logger.trace(message);
        ReportPortal.emitLog(message, "TRACE", new Date());
    }

    public void error(String message) {
        logger.error(message);
        ReportPortal.emitLog(message, "ERROR", new Date());
    }

    public void error(String message, Exception e) {
        logger.error(message, e);
        ReportPortal.emitLog(message, "ERROR", new Date());
    }

    public void attachedScreenShot(String testCase, File f){
        logger.info("Attaching Screenshpt to report Portal");
        ReportPortal.emitLog(testCase, "FAIL", Calendar.getInstance().getTime(), f);
    }


}
