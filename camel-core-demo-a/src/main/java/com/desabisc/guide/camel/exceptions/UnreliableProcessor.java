package com.desabisc.guide.camel.exceptions;

import java.util.Random;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor that randomly fails to demonstrate error handling
 */
public class UnreliableProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(UnreliableProcessor.class);
    private final Random random = new Random();
    
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("UnreliableProcessor: Processing exchange...");
        
        // Randomly fail 50% of the time
        if (random.nextBoolean()) {
            throw new RuntimeException("Random failure in UnreliableProcessor");
        }
        
        exchange.getIn().setBody("Successfully processed by UnreliableProcessor");
        logger.info("UnreliableProcessor: Processing completed successfully");
    }
}