package seda.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates orders - randomly marks some as invalid for demonstration
 */
public class ValidationProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(ValidationProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    String processedOrder = exchange.getIn().getBody(String.class);

    // Simulate validation logic - 80% success rate
    boolean isValid = Math.random() > 0.2;

    exchange.getIn().setHeader("valid", isValid);
    exchange.getIn().setHeader("validatedAt", System.currentTimeMillis());

    logger.info("Order validation result for {}: {}", processedOrder, isValid);
  }
}