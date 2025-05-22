package com.desabisc.guide.camel.exceptions;

import com.desabisc.guide.camel.processeg.ega.ProcessRouterEg;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class ExceptionHandlingMain {
  public static void main(String[] args) throws Exception {
    // Create a CamelContext
    CamelContext context = new DefaultCamelContext();

    // Add routes
    context.addRoutes(new ErrorHandlingRoutes());

    // Start the Camel context
    context.start();

    // Keep the application running
    Thread.sleep(60000); // Run for 60 seconds

    // Shutdown the context
    context.stop();
  }
}
