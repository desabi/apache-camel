package com.desabi.guide.camel.core.exceptions.atrycatchfinally;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class TryCatchFinallyRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    // Basic error handling with try-catch
    from("timer://basicErrorTimer?period=10000&repeatCount=5")
        .routeId("try-catch-finally-route")
        .log("Processing message in basic error route...")
        .doTry()
          .process(new UnreliableProcessor())
          .log("Message processed successfully")
        .doCatch(RuntimeException.class)
          .log(LoggingLevel.ERROR, "Caught exception: ${exception.message}")
          .setBody(simple("Error occurred: ${exception.message}"))
        .doFinally()
          .log("Cleanup operations completed")
        .end()
        .to("direct:result");

    // Result and logging routes
    from("direct:result")
        .routeId("result-route")
        .log(LoggingLevel.INFO, "Final result: ${body}");
  }
}