package org.oneframework.runner;

import org.oneframework.utils.ATDExecutor;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class MainClass {

    ATDExecutor executor = new ATDExecutor();
    @Test
    public void starTest() throws Exception {
        System.out.println("test started");
        executor.constructXMLAndTriggerParallelRunner(new ArrayList<>(),"tests" ,1,"distribute");
    }
}