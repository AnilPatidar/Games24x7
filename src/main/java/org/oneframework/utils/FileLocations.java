package org.oneframework.utils;

import java.io.File;

import static org.oneframework.utils.OverriddenVariable.getOverriddenStringValue;

public interface FileLocations {
    String OUTPUT_DIRECTORY =
            getOverriddenStringValue("OUTPUT_DIRECTORY") != null
            ? File.separator + getOverriddenStringValue("OUTPUT_DIRECTORY")
                    + File.separator
            : File.separator + "target" + File.separator;

    String PARALLEL_XML_LOCATION = OUTPUT_DIRECTORY + "parallel.xml";
    String PARALLEL_XML_LOCATION_NEW = OUTPUT_DIRECTORY + "parallel2.xml";
    String REPORTS_DIRECTORY = OUTPUT_DIRECTORY + "reports" + File.separator;

    String SCREENSHOTS_DIRECTORY = OUTPUT_DIRECTORY + "screenshot" + File.separator;
    String ANDROID_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "android" + File.separator;
    String IOS_SCREENSHOTS_DIRECTORY = SCREENSHOTS_DIRECTORY + "iOS" + File.separator;

    String APPIUM_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "appiumlogs" + File.separator;
    String ADB_LOGS_DIRECTORY = OUTPUT_DIRECTORY + "adblogs" + File.separator;
    String DEVICE_LOGS_DIRECTORY = "deviceLogs" + File.separator;
    String TEST_LOGS_DIRECTORY = "testLogs" + File.separator;
    String DERIVED_DATA = OUTPUT_DIRECTORY + "derivedData" + File.separator;
}
