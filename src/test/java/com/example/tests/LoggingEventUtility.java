package com.example.tests;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class LoggingEventUtility {

  public static ListAppender<ILoggingEvent> getListAppenderForClass(Class<?> clazz, Level level) {
    Logger logger = (Logger) LoggerFactory.getLogger(clazz);
    logger.setLevel(level);
    ListAppender<ILoggingEvent> loggingEventListAppender = new ListAppender<>();

    loggingEventListAppender.start();
    logger.addAppender(loggingEventListAppender);
    return loggingEventListAppender;
  }
}
