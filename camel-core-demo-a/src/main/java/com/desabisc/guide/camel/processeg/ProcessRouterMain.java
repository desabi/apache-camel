package com.desabisc.guide.camel.processeg;

import com.desabisc.guide.camel.MyMainApp.MyRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class ProcessRouterMain {
  public static void main(String[] args) throws Exception {
    // Create a CamelContext
    CamelContext context = new DefaultCamelContext();

    // Add routes
    context.addRoutes(new ProcessRouterEg());

    // Start the Camel context
    context.start();

    // Keep the application running
    Thread.sleep(20000); // Run for 20 seconds

    // Shutdown the context
    context.stop();
  }
}
