package com.desabi.guide.camel.core.exceptions.atrycatchfinally;

import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class TryCatchFinallyMain {

  public static void main(String[] args) throws Exception {
    // create camel context
    // Using try with resources, is required to decorate the main method with throws Exception
    try (CamelContext camelContext = new DefaultCamelContext()) {

      // Add routes to the context
      camelContext.addRoutes(new TryCatchFinallyRoute());

      // Start the context
      camelContext.start();

      System.out.println("Camel context started");

      // Keep the application running
      Thread.sleep(TimeUnit.SECONDS.toMillis(60)); // Run for 60 seconds

      // Stop the context
      camelContext.stop();
    }

    System.out.println("Camel Context stopped.");
  }

}
