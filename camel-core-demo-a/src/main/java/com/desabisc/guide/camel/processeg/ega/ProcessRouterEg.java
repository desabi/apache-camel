package com.desabisc.guide.camel.processeg.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ProcessRouterEg extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("timer:simple?period=1000")
        .log(">>>>> Producer: timer:simple?period=1000")
        .setBody(constant("Custom Body defined in setBody() DLS method"))
        .process(new Processor() {
          @Override
          public void process(Exchange exchange) throws Exception {
            log.info(">>>>> Producer, Body In: {}", exchange.getIn().getBody());
            exchange.getOut().setBody("Custom Body defined in process, in exchange.getOut().setBody()");
            exchange.getOut().setHeader("MyCustomHeader", "header1 value eg");
          }
        })
        .to("direct:processMessage")
        .end();

    from("direct:processMessage")
        .log("***** Consumer: direct:processMessage")
        .log("***** Body: ${body}, Header: ${header.MyCustomHeader}")
        .end();
  }
}
