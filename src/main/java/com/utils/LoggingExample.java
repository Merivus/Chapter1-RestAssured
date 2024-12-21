package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
  private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

  public static void main(String[] args) {
    logger.info("Starting test: exampleLogging");
    logger.debug("This is a DEBUG log message.");
    logger.error("This is an ERROR log message.");
    logger.info("Test finished: exampleLogging");
  }
}
