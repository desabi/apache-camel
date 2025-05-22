package com.desabisc.guide.camel.exceptions;

import java.util.Random;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor that throws specific types of exceptions.
 * Used in: from("timer://onExceptionTimer?period=15000&repeatCount=3")
 */
class SpecificExceptionProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(SpecificExceptionProcessor.class);
    private final Random random = new Random();
    
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SpecificExceptionProcessor: Processing exchange...");
        
        int choice = random.nextInt(3);
        logger.info("Choice: {}", choice);

        switch (choice) {
            case 0:
                throw new IllegalArgumentException("Invalid argument provided");
            case 1:
                throw new NullPointerException("Null pointer encountered");
            default:
                exchange.getIn().setBody("Successfully processed by SpecificExceptionProcessor");
                logger.info("SpecificExceptionProcessor: Processing completed successfully");
        }
    }
}