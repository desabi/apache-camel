package com.desabi.guide.camel.testing.ega.timera;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRouteExample extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("timer:timer-example?period=3000&repeatCount=5")
        .routeId("timer-route-example")
        .setBody(simple("Current time is ${date:now:yyyy-MM-dd HH:mm:ss}"))
        .log("Message Body: ${body}");
  }
}
