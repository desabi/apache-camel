package com.desabisc.guide.camel.exceptions;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ErrorHandlingRoutes extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    // Route 1: Basic error handling with try-catch
    /*from("timer://basicErrorTimer?period=10000&repeatCount=5")
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
        .to("direct:result");*/

    // Route 2: OnException error handling with specific exception types
    from("timer://onExceptionTimer?period=15000&repeatCount=4")
        .routeId("on-exception-route")
        .onException(IllegalArgumentException.class)
          .handled(true)
          .log(LoggingLevel.ERROR, "Handling IllegalArgumentException: ${exception.message}")
          .setBody(constant("Invalid argument provided"))
          .to("direct:illegalArgumentHandler")
        .end()
          .onException(NullPointerException.class)
          .handled(true)
          .log(LoggingLevel.ERROR, "Handling NullPointerException: ${exception.message}")
          .setBody(constant("Null pointer encountered"))
          .to("direct:nullPointerHandler")
        .end()
        .log("Processing message with specific exception handling...")
        .process(new SpecificExceptionProcessor())
        .to("direct:result");

    from("direct:illegalArgumentHandler")
        .routeId("illegal-argument-handler")
        .log("Handling illegal argument error")
        .to("direct:errorLog");

    from("direct:nullPointerHandler")
        .routeId("null-pointer-handler")
        .log("Handling null pointer error")
        .to("direct:errorLog");

    from("direct:errorLog")
        .routeId("error-log-route")
        .log(LoggingLevel.ERROR, "Error logged: ${body}")
        .to("file://logs/errors?fileName=error-${date:now:yyyyMMdd-HHmmss}.txt");

    // Result and logging routes
    from("direct:result")
        .routeId("result-route")
        .log("Final result: ${body}");
  }
}
