package org.oneframework.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.ScreenRecordingUploadOptions;
import lombok.NonNull;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.Logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private static final String videoFilePath = "/Video/";




    private ADBUtilities() {

    }
    public static final String ADB_EXECUTABLE = Paths.get(getAndroidPath(), "platform-tools", "adb").toString();
    private static final String PROPERTY_REGEX = "(?<=\\[).+?(?=\\])";


    /**
     * @return
     */
    public static String getAndroidPath() {
        Map <String, String> map = System.getenv();
        String androidHome = System.getenv("ANDROID_HOME");
//        String androidHome = "/Users/jenkins/Library/Android/sdk";
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

    public static String dumpAdbLogs(@NonNull String fileName, AndroidDriver<AndroidElement> driver) throws InterruptedException, IOException {
        getLogsFolderPath(fileName);
        Logs logs = driver.manage().logs();
        LogEntries logEntries = logs.get("logcat");
        File logFile = new File(System.getProperty("user.dir")+ logsFilePath +fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));

        List<LogEntry> entries = logEntries.getAll();
        for (LogEntry entry : entries) {
            writer.write(entry.getTimestamp() + " " + entry.getLevel() + " " + entry.getMessage());
            writer.newLine();
        }

        writer.close();
        System.out.println("Android logs saved to " + logFile.getAbsolutePath());
        return logFile.getAbsolutePath();
    }

    public static void dumpVideo(AndroidDriver<AndroidElement> driver,String fileName) throws IOException {
        getVideoFolderPath(fileName);
        AndroidStartScreenRecordingOptions startScreenRecordingOptions=new AndroidStartScreenRecordingOptions()
                .withTimeLimit(Duration.ofSeconds(30));
        driver.startRecordingScreen(startScreenRecordingOptions);
        var base64String=driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        Path path = Paths.get(fileName);
        Files.write(path, data);
    }

    public static void dumpScreenShot(File srcFile,String fileName) throws IOException {
        getScreenshotFolderPath(fileName);
        File destFile=new File(System.getProperty("user.dir")+screenshotFilePath+fileName);
        FileUtils.copyFile(srcFile,destFile);
        System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
    }

    public static void getLogsFolderPath(String fileName) throws IOException {
        File logFile = new File(System.getProperty("user.dir")+ logsFilePath +fileName);
        logFile.getParentFile().mkdirs();
        logFile.createNewFile();
    }

    public static void getScreenshotFolderPath(String fileName) throws IOException {
        File screenShotFile = new File(System.getProperty("user.dir")+ screenshotFilePath +fileName);
        screenShotFile.getParentFile().mkdirs();
        screenShotFile.createNewFile();
    }

    public static void getVideoFolderPath(String fileName) throws IOException {
        File videoFile = new File(System.getProperty("user.dir")+ videoFilePath +fileName);
        videoFile.getParentFile().mkdirs();
        videoFile.createNewFile();
    }

}