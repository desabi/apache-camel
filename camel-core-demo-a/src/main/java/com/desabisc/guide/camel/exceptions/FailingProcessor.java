package com.desabisc.guide.camel.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processor that always fails to demonstrate dead letter channel
 */
class FailingProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(FailingProcessor.class);
    
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("FailingProcessor: Processing exchange...");
        throw new RuntimeException("FailingProcessor always fails");
    }
}