package com.desabisc.guide.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.CamelContext;

public class MyMainApp {

  public static void main(String[] args) throws Exception {

    // Create a CamelContext
    CamelContext context = new DefaultCamelContext();

    // Add routes
    context.addRoutes(new MyRouteBuilder());

    System.out.println("Starting Camel application...");

    // Start the Camel context
    context.start();

    // Keep the application running
    Thread.sleep(Long.MAX_VALUE);
  }

  // Inner RouteBuilder class
  public static class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
      // Define your routes here
      from("timer:simple?period=5000")
          .setBody(constant("Hello Camel!"))
          .log(">>> ${body}");
    }
  }
}
