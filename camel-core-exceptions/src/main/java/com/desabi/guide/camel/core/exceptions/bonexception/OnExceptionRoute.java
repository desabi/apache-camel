package com.desabi.guide.camel.core.exceptions.bonexception;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class OnExceptionRoute extends RouteBuilder {

  // OnException error handling with specific exception types
  @Override
  public void configure() throws Exception {
    from("timer://on-Exception-Example-Timer?period=15000&repeatCount=4")
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
        .routeId("illegal-argument-handler-route")
        .log("Handling illegal argument error")
        .to("direct:errorLog");

    from("direct:nullPointerHandler")
        .routeId("null-pointer-handler-route")
        .log("Handling null pointer error")
        .to("direct:errorLog");

    from("direct:errorLog")
        .routeId("error-log-route")
        .log(LoggingLevel.ERROR, "Error logged: ${body}")
        .to("file://logs/errors?fileName=error-${date:now:yyyyMMdd-HHmmss}.txt");

    from("direct:result")
        .routeId("result-route")
        .log("Final result: ${body}");

    // TODO: onWhen()
    // TODO: Route leve ok, Global Level X
    //
  }
}
