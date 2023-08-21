package org.oneframework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import lombok.NonNull;
import org.apache.commons.io.FileUtils;

import static org.oneframework.utils.CommandLineExecutor.exec;
import static org.oneframework.utils.StringUtils.splitLines;


/**
 * @author jitu-patel
 *
 */

public final class ADBUtilities {

    private static final String MODEL = "ro.product.model";
    private static final String MANUFACTURE = "ro.product.brand";
    private static final String OS = "ro.build.version.release";
    private static final String DEVICENAME = "ro.product.model";
    private static final String RESOLUTION = "Physical size";
    private static final String logsFilePath = "/Logs/android/";
    private static final String screenshotFilePath = "/Screenshot/";




    private ADBUtilities() {

    }
    public static final String ADB_EXECUTABLE = Paths.get(getAndroidPath(), "platform-tools", "adb").toString();
    private static final String PROPERTY_REGEX = "(?<=\\[).+?(?=\\])";


    /**
     * @return
     */
    public static String getAndroidPath() {
        Map <String, String> map = System.getenv();
        //String androidHome = System.getenv("ANDROID_HOME");
        String androidHome = "/Users/jenkins/Library/Android/sdk";
        return StringUtils.isBlank(androidHome.isEmpty()) ? System.getProperty("android.home", "/opt/android-sdk")
                : androidHome.trim();
    }

    /**
     * @return
     */

    public static List<String> connectedDevices() {
        CommandLineResponse response = exec(ADB_EXECUTABLE + " devices | tail -n +2");
        if (response.getExitCode() == 0) {
            List<String> split = splitLines(response.getStdOut());
            return split.stream()
                    .filter(e -> e.contains("device") && !e.trim().isEmpty())
                    .map(e -> e.split("\\s+")[0].trim())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }







    public static String getDeviceName(@NonNull String deviceId) {
        String cmd = String.format(
                "%s -s %s shell cat /proc/meminfo | grep -i MemTotal | cut -d ' ' -f 9", ADB_EXECUTABLE, deviceId);
        CommandLineResponse response = exec(cmd);
        if (response.getExitCode() == 0) {
            return response.getStdOut();
        }
        return null;
    }

    public static String getAndroidProperties(@NonNull String deviceId) {
        String cmd = String.format(
                "%s -s %s shell getprop", ADB_EXECUTABLE, deviceId);
        CommandLineResponse response = exec(cmd);
        if (response.getExitCode() == 0) {
            return response.getStdOut();
        }
        return null;
    }

    public static String getAndroidDeviceResolution(@NonNull String deviceId) {
        String cmd = String.format(
                "%s -s %s shell wm size", ADB_EXECUTABLE, deviceId);
        CommandLineResponse response = exec(cmd);
        if (response.getExitCode() == 0) {
            return response.getStdOut();
        }
        return null;
    }


    public static String runAndroidDeviceCommand(@NonNull String deviceId,@NonNull String command) {
        String cmd = String.format(
                "%s -s %s "+command, ADB_EXECUTABLE, deviceId);
        System.out.println(cmd);
        CommandLineResponse response = exec(cmd);
        if (response.getExitCode() == 0) {
            return response.getStdOut();
        }
        return null;
    }

    public static String dumpAdbLogs(@NonNull String deviceId,@NonNull String fileName){
        String cmd = String.format(
                "%s -s %s "+"logcat >"+System.getProperty("user.dir")+ logsFilePath +fileName, ADB_EXECUTABLE, deviceId);
        System.out.println(cmd);
        CommandLineResponse response = exec(cmd);
        if (response.getExitCode() == 0) {
            return response.getStdOut();
        }
        return null;
    }

    public static void dumpScreenShot(File srcFile,String fileName) throws IOException {
        File destFile=new File(System.getProperty("user.dir")+screenshotFilePath+fileName);
        FileUtils.copyFile(srcFile,destFile);
        System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
    }


}