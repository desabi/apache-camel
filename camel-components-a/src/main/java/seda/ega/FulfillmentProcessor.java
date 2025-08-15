package seda.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles successful order fulfillment
 */
public class FulfillmentProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(FulfillmentProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    String validOrder = exchange.getIn().getBody(String.class);

    // Simulate fulfillment processing
    Thread.sleep(300);

    exchange.getIn().setBody("Fulfilled-" + validOrder);
    exchange.getIn().setHeader("fulfilledAt", System.currentTimeMillis());

    logger.info("Order fulfilled: {}", validOrder);
  }
}