package org.oneframework.utils;

import org.apache.commons.io.FileUtils;
import org.oneframework.config.AndroidDeviceModel;
import org.oneframework.config.DeviceConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtility {

    public static final String screenshotFilePath = "/Screenshot_X/";


    public static File getFile(String fileName) throws IOException {
        if (FileUtility.class.getClassLoader().getResourceAsStream(fileName) != null) {
            InputStream resourceAsStream = FileUtility.class.getClassLoader().getResourceAsStream(fileName);
            File file = new File(fileName, "");
            FileUtils.copyInputStreamToFile(resourceAsStream, file);
            return file;
        } else {
            return new File(fileName);
        }
    }

    public static void createDirectoryIfNotExist(File directory) {
        if (!directory.exists()) {
            File dir = new File("./" + directory);
            dir.mkdirs();
        }
    }

    public static void copyFileToDirectory(File file, File directory) throws IOException {
        createDirectoryIfNotExist(directory);
        FileUtils.copyFileToDirectory(file, directory, true);
    }

    public static void forceDelete(File file) throws IOException {
        file.delete();
    }

    public static void takeScreenShot(WebElement element) throws IOException {
        File screenshotFile = element.getScreenshotAs(OutputType.FILE);
        // Save the cropped screenshot to a file
        File destinationFile = new File(System.getProperty("user.dir")+ screenshotFilePath +"IMG_"+System.currentTimeMillis()+".png");
        destinationFile.getParentFile().mkdirs();
        destinationFile.createNewFile();
        FileUtils.copyFile(screenshotFile, destinationFile);
        System.out.println("Screenshot of element saved to: " + destinationFile.getAbsolutePath());
    }

    public static void deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        if (!folder.delete()) {
            System.out.println("Failed to delete folder: " + folder);
        }
    }

    public static void pushMultipleFiles(File folder) throws IOException {
        AndroidDeviceModel device = DeviceConfig.readAndroidDeviceConfig().getAndroidDeviceByName("pixel");
        String udid = device.getDeviceName();
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    ADBUtilities.runAndroidDeviceCommand(udid, "push "+file.getAbsolutePath() + " " + "/sdcard/DCIM/Camera" );
                }
            }
        }
    }

    public static void pushFile(File folder) {
        if (folder.isDirectory()) {
           ADBUtilities.runAndroidDeviceCommand( "push "+folder + " " + "/sdcard/" );
        }
//        if (folder.isDirectory()) {
//            File[] files = folder.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    ADBUtilities.runAndroidDeviceCommand( "push "+file.getAbsolutePath() + " " + "/sdcard/DCIM/Camera");
//                }
//            }
//        }
    }
}
