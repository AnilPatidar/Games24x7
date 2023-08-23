package org.oneframework.utils;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static java.lang.System.getProperty;
import static java.util.Collections.addAll;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.oneframework.utils.ConfigFileManager.*;
import static org.oneframework.utils.FileLocations.PARALLEL_XML_LOCATION;
import static org.oneframework.utils.OverriddenVariable.getOverriddenStringValue;

public class ATDExecutor {
 //   private final DeviceAllocationManager deviceAllocationManager;
    private final List<String> items = new ArrayList<String>();
    private final List<String> listeners = new ArrayList<>();
    private final List<String> groupsInclude = new ArrayList<>();
    private final List<String> groupsExclude = new ArrayList<>();
    private static final String ANDROID = "android";
    private static final String Web = "Web";
    private static final String IOS = "iOS";

    public ATDExecutor(/*DeviceAllocationManager deviceAllocationManager*/) {
      //  this.deviceAllocationManager = deviceAllocationManager;
    }

    public boolean
    constructXMLAndTriggerParallelRunner(List<String> test, String pack,
                                                        int deviceCount, String executionType)
            throws Exception {
        boolean result;
        String suiteName = SUITE_NAME.get();
        String categoryName = CATEGORY.get();
        Set<Method> setOfMethods = getMethods(pack);
        String runnerLevel = RUNNER_LEVEL.get();
        if (executionType.equalsIgnoreCase("distribute")) {
            if (runnerLevel != null && runnerLevel.equalsIgnoreCase("class")) {
             /*   constructXmlSuiteForClassLevelDistributionRunner(test, getTestMethods(setOfMethods),
                        suiteName, categoryName, deviceCount);*/
            } else {
                constructXmlSuiteForMethodLevelDistributionRunner(test,
                        getTestMethods(setOfMethods), suiteName, categoryName, deviceCount);
            }
        } else {
            //Runs test parallel, all cases on all the devices
           // constructXmlSuiteForParallelRunner(test, getTestMethods(setOfMethods),
                 //   suiteName, categoryName, deviceCount);
        }
        result = testNGParallelRunner();
        //figlet("Test Completed");
        return result;
    }

   /* public XmlSuite constructXmlSuiteForParallelRunner(List<String> tests,
                                                       Map<String, List<Method>> methods,
                                                       String suiteName, String categoryName,
                                                       int deviceCount) {
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("com.appium.manager.AppiumParallelTestListener");
        listeners.add("com.appium.utils.RetryListener");
        include(listeners, LISTENERS);
        include(groupsInclude, INCLUDE_GROUPS);
        include(groupsExclude, EXCLUDE_GROUPS);
        XmlSuite suite = new XmlSuite();
        suite.setName(suiteName);
        suite.setThreadCount(deviceCount);
        suite.setDataProviderThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
        suite.setVerbose(2);
        suite.setListeners(listeners);
        for (int i = 0; i < deviceCount; i++) {
            XmlTest test = new XmlTest(suite);
            test.setName(categoryName + "-" + i);
            test.setPreserveOrder(false);
            final AppiumDevice appiumDevice = deviceAllocationManager.getDevices().get(i);
            test.addParameter("device", appiumDevice.getDevice().getUdid());
            test.addParameter("hostName", appiumDevice.getHostName());
            test.setIncludedGroups(groupsInclude);
            test.setExcludedGroups(groupsExclude);
            List<XmlClass> xmlClasses = writeXmlClass(tests, methods);
            test.setXmlClasses(xmlClasses);
        }
        writeTestNGFile(suite);
        return suite;
    }*/

   /* public XmlSuite constructXmlSuiteForClassLevelDistributionRunner(List<String> tests,
                   Map<String, List<Method>> methods,
                   String suiteName, String categoryName, int deviceCount) {
        XmlSuite suite = new XmlSuite();
        suite.setName(suiteName);
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
        suite.setVerbose(2);
       // listeners.add("com.appium.manager.AppiumParallelMethodTestListener");
       // listeners.add("com.appium.utils.RetryListener");
        include(listeners, LISTENERS);
        suite.setListeners(listeners);
        XmlTest test = new XmlTest(suite);
        test.setName(categoryName);
        //TODO make them parameterised, read from system prop
        String platform = getOverriddenStringValue("platform");
        String platformType = getOverriddenStringValue("platformType");
        test.addParameter("platformType", platformType);
        test.addParameter("platformName", platform);
        include(groupsExclude, EXCLUDE_GROUPS);
        include(groupsInclude, INCLUDE_GROUPS);
        test.setIncludedGroups(groupsInclude);
        test.setExcludedGroups(groupsExclude);
        List<XmlClass> xmlClasses = writeXmlClass(tests, methods);
        test.setXmlClasses(xmlClasses);
        writeTestNGFile(suite);
        return suite;
    }*/


    //We need this
    public XmlSuite constructXmlSuiteForMethodLevelDistributionRunner(List<String> tests,
                             Map<String, List<Method>> methods, String suiteName,
                             String category, int deviceCount) {
        include(groupsInclude, INCLUDE_GROUPS);
        XmlSuite suite = new XmlSuite();
        suite.setThreadCount(deviceCount);
        suite.setDataProviderThreadCount(deviceCount);
        suite.setVerbose(2);
        suite.setParallel(ParallelMode.METHODS);
        listeners.add("com.epam.reportportal.testng.ReportPortalTestNGListener");
        //listeners.add("com.appium.utils.RetryListener");
        listeners.add("org.oneframework.allureReport.TestListener");
        include(listeners, LISTENERS);
        suite.setListeners(listeners);

        String platforms = getOverriddenStringValue("platform");
        String platformTypes = getOverriddenStringValue("platformType");

        //platformType=mobile,web;platform=both,chrome
        //platformType=mobile;platform=android,ios
        //platformType=web;platform=chrome,firefox
        //platformType=mobile,web;platform=android,firefox

        List<String> mobilePlatforms= new ArrayList<>();
        if(platformTypes.contains("mobile")){
            if(platforms.contains("both")){
                mobilePlatforms.add("android");
                mobilePlatforms.add("ios");
            }
            if(platforms.contains("ios")){
                mobilePlatforms.add("ios");
            }
            if(platforms.contains("android")){
                mobilePlatforms.add("android");
            }
        }
        List<String> webPlatforms= new ArrayList<>();
        if(platformTypes.contains("web")){
            if(platforms.contains("chrome")){
                webPlatforms.add("chrome");
            }
            if(platforms.contains("firefox")){
                webPlatforms.add("firefox");
            }
        }

        for(int i=0;i<mobilePlatforms.size();i++){
                CreateGroups createGroups = new CreateGroups(tests, methods, category, suite).invoke();
                List<XmlClass> xmlClasses = createGroups.getXmlClasses();
                XmlTest test = createGroups.getTest();
                test.setName("test "+i);
               // String platform = getOverriddenStringValue("platform");
               // String platformType = getOverriddenStringValue("platformType");
                test.addParameter("platformType", "mobile");
                test.addParameter("platformName", mobilePlatforms.get(i));
                List<XmlClass> writeXml = createGroups.getWriteXml();
                for (XmlClass xmlClass : xmlClasses) {
                    writeXml.add(new XmlClass(xmlClass.getName()));
                    test.setClasses(writeXml);
                }
        }

        for(int i=0;i<webPlatforms.size();i++){
            CreateGroups createGroups = new CreateGroups(tests, methods, category, suite).invoke();
            List<XmlClass> xmlClasses = createGroups.getXmlClasses();
            XmlTest test = createGroups.getTest();
            test.setName("test "+i);
            // String platform = getOverriddenStringValue("platform");
            // String platformType = getOverriddenStringValue("platformType");
            test.addParameter("platformType", "web");
            test.addParameter("platformName", webPlatforms.get(i));
            List<XmlClass> writeXml = createGroups.getWriteXml();
            for (XmlClass xmlClass : xmlClasses) {
                writeXml.add(new XmlClass(xmlClass.getName()));
                test.setClasses(writeXml);
            }
        }
        writeTestNGFile(suite);
        return suite;
    }

    public boolean testNGParallelRunner() {
        TestNG testNG = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add(getProperty("user.dir") + PARALLEL_XML_LOCATION);
        testNG.setTestSuites(suites);
        testNG.run();
        return testNG.hasFailure();
    }

    private Set<Method> getMethods(String pack) throws MalformedURLException {
        URL newUrl;
        List<URL> newUrls = new ArrayList<>();
        //Adds all the packages names (Separated by comma)
        addAll(items, pack.split("\\s*,\\s*"));
        int a = 0;
        Collection<URL> urls = ClasspathHelper.forPackage(items.get(a));
        Iterator<URL> iter = urls.iterator();

        URL url = null;

        while (iter.hasNext()) {
            url = iter.next();
            break;
           // if (url.toString().contains("test-classes")) {
            //    break;
          //  }
        }
        for (String item : items) {
            newUrl = new URL(url.toString() + item.replaceAll("\\.", "/"));
            newUrls.add(newUrl);
            a++;
        }
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(newUrls)
            .setScanners(new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(Test.class);
    }

    private List<XmlClass> writeXmlClass(List<String> testCases, Map<String,
            List<Method>> methods) {
        List<XmlClass> xmlClasses = new ArrayList<>();
        for (String className : methods.keySet()) {
            XmlClass xmlClass = new XmlClass();
            xmlClass.setName(className);
            if (className.contains("Test")) {
                if (testCases.size() == 0) {
                    xmlClasses.add(xmlClass);
                } else {
                    for (String s : testCases) {
                        for (String item : items) {
                            String testName = item.concat("." + s);
                            if (testName.equals(className)) {
                                xmlClasses.add(xmlClass);
                            }
                        }
                    }
                }
            }
        }
        return xmlClasses;
    }

    private void writeTestNGFile(XmlSuite suite) {
        try (FileWriter writer = new FileWriter(new File(
            getProperty("user.dir") + PARALLEL_XML_LOCATION))) {
            writer.write(suite.toXml());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Test ng file is successfully written");
    }

    private void include(List<String> groupsInclude, ConfigFileManager config) {
        String listItems = config.get();
        if (isNotEmpty(listItems)) {
            addAll(groupsInclude, listItems.split("\\s*,\\s*"));
        }
    }

    public Map<String, List<Method>> getTestMethods(Set<Method> methods) {
        Map<String, List<Method>> listOfMethods = new HashMap<>();
        methods.forEach(method -> {
            List<Method> methodsList = listOfMethods.computeIfAbsent(
                method.getDeclaringClass().getPackage().getName()
                    + "." + method.getDeclaringClass()
                    .getSimpleName(), k -> new ArrayList<>());
            methodsList.add(method);
        });
        return listOfMethods;
    }

    private class CreateGroups {
        private List<String> tests;
        private Map<String, List<Method>> methods;
        private String category;
        private XmlSuite suite;
        private List<XmlClass> xmlClasses;
        private XmlTest test;
        private List<XmlClass> writeXml;

        public CreateGroups(List<String> tests, Map<String, List<Method>> methods,
                            String category, XmlSuite suite) {
            this.tests = tests;
            this.methods = methods;
            this.category = category;
            this.suite = suite;
        }

        public List<XmlClass> getXmlClasses() {
            return xmlClasses;
        }

        public XmlTest getTest() {
            return test;
        }

        public List<XmlClass> getWriteXml() {
            return writeXml;
        }

        public CreateGroups invoke() {
            xmlClasses = writeXmlClass(tests, methods);
            test = new XmlTest(suite);
            test.setName(category);
            //test.addParameter("device", "");
            include(groupsExclude, EXCLUDE_GROUPS);
            test.setIncludedGroups(groupsInclude);
            test.setExcludedGroups(groupsExclude);
            writeXml = new ArrayList<>();
            return this;
        }
    }
}