package org.oneframework.utils;

import lombok.Data;


public class CommandLineResponse {

    private int exitCode;

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public String getStdOut() {
        return stdOut;
    }

    public void setStdOut(String stdOut) {
        this.stdOut = stdOut;
    }

    public String getErrOut() {
        return errOut;
    }

    public void setErrOut(String errOut) {
        this.errOut = errOut;
    }

    private String stdOut;
    private String errOut;



}
