package com.desabisc.guide.camel.bean.egb;

import org.apache.camel.builder.RouteBuilder;

public class RoutePerson extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    from("timer:simple?period=1000")
        .process(new ProcessorSetPerson())
        .to("direct:processPerson")
        .end();

    from("direct:processPerson")
        .process(new ProcessorGetPerson())
        .end();
  }
}
