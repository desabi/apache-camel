package seda.ega;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles order errors
 */
public class ErrorProcessor implements Processor {

  private static final Logger logger = LoggerFactory.getLogger(ErrorProcessor.class);

  @Override
  public void process(Exchange exchange) throws Exception {
    String errorOrder = exchange.getIn().getBody(String.class);

    exchange.getIn().setBody("Error-" + errorOrder);
    exchange.getIn().setHeader("errorAt", System.currentTimeMillis());

    logger.warn("Order error processed: {}", errorOrder);
  }
}