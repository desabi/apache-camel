package com.desabi.guide.camel.core.exceptions.bonexception;

import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OnExceptionMain {

  private static final Logger logger = LoggerFactory.getLogger(OnExceptionMain.class);

  public static void main(String[] args) {

    try (CamelContext camelContext = new DefaultCamelContext()) {

      // Add routes to the camel context
      camelContext.addRoutes(new OnExceptionRoute());

      // Start camel context
      camelContext.start();
      logger.info("Camel Context started successfully");

      // Keep the application running for X seconds
      Thread.sleep(TimeUnit.SECONDS.toMillis(30));

      // Shutdown Camel Context
      camelContext.stop();

    } catch (Exception e) {
      logger.error("Error handling Camel Context: {}", e.getMessage());
    }
  }

}
