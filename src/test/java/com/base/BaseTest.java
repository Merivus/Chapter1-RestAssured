package com.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.utils.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
  protected ExtentReports extent;
  protected ThreadLocal<ExtentTest> test = new ThreadLocal<>();
  protected ThreadLocal<Logger> logger = new ThreadLocal<>();

  @BeforeClass
  public void setup() {
    extent = ExtentManager.getInstance();
  }

  @BeforeMethod
  public void createTest(Method method) {
    test.set(extent.createTest(method.getName()));
    logger.set(LoggerFactory.getLogger(method.getDeclaringClass()));
  }

  protected ExtentTest getTest() {
    return test.get();
  }

  protected Logger getLogger() {
    return logger.get();
  }

  @AfterClass
  public void tearDown() {
    extent.flush(); // Print report
  }
}
