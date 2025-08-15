package seda.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Generates sample order messages
 */
public class MessageGenerator implements Processor {

  private int counter = 1;

  @Override
  public void process(Exchange exchange) throws Exception {
    String orderId = "ORDER-" + String.format("%03d", counter++);
    exchange.getIn().setBody(orderId);
    exchange.getIn().setHeader("timestamp", System.currentTimeMillis());
  }
}