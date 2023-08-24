package org.oneframework.runner;

import org.oneframework.utils.ATDExecutor;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TestNgRunner {

    ATDExecutor executor = new ATDExecutor();
    @Test
    public void starTest() throws Exception {
        try {
            boolean result=executor.constructXMLAndTriggerParallelRunner(new ArrayList<>(), "tests", 1, "distribute");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}