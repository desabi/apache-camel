package com.desabisc.guide.camel.bean.egb;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessorSetPerson implements Processor {
  @Override
  public void process(Exchange exchange) throws Exception {
    Person person = new Person("abi", 37);
    exchange.getOut().setBody(person);
  }
}
