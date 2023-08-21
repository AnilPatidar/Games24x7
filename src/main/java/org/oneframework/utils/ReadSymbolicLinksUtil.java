package org.oneframework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadSymbolicLinksUtil {

    public String getRealPathOfAppium() {
        try {
            String[] command = null;
            boolean isDarwin = new BufferedReader(new InputStreamReader(new ProcessBuilder("uname").start().getInputStream()))
                    .lines()
                    .anyMatch(line -> line.equals("Darwin"));
            if (isDarwin) {
                command  = new String[]{"sh", "-c", "echo $(greadlink -f $(which appium))"};
            } else {
                command  = new String[]{"sh", "-c", "echo $(readlink -f $(which appium))"};
            }
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String realPath = reader.readLine();

            if (realPath != null) {
                System.out.println("Real path of Appium: " + realPath);
                return realPath;
            } else {
                System.out.println("Failed to get real path of Appium.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}