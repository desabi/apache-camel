package com.desabisc.guide.camel.exceptions;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ErrorHandlingRoutes extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    // Route 1: Basic error handling with try-catch
    from("timer://basicErrorTimer?period=10000&repeatCount=5")
        .routeId("basic-error-route")
        .log("Processing message in basic error route...")
        .doTry()
          .process(new UnreliableProcessor())
          .log("Message processed successfully")
        .doCatch(RuntimeException.class)
          .log(LoggingLevel.ERROR, "Caught exception: ${exception.message}")
          .setBody(constant("Error occurred: ${exception.message}"))
        .doFinally()
          .log("Cleanup operations completed")
        .end()
        .to("direct:result");

    // Result and logging routes
    from("direct:result")
        .routeId("result-route")
        .log("Final result: ${body}");
  }
}
