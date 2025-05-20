package com.desabisc.guide.camel.bean.egb;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessorGetPerson implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    System.out.println("Body in. ");
    Person body = exchange.getIn().getBody(Person.class);
    System.out.println("Person name: " + body.getName());
  }
}
