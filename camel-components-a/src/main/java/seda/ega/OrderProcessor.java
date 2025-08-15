package seda.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulates order processing with some delay
 */
public class OrderProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(OrderProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    String orderId = exchange.getIn().getBody(String.class);

    // Simulate processing time
    Thread.sleep(500);

    exchange.getIn().setBody("Processed-" + orderId);
    exchange.getIn().setHeader("processedAt", System.currentTimeMillis());

    logger.info("Order processed: {}", orderId);
  }
}