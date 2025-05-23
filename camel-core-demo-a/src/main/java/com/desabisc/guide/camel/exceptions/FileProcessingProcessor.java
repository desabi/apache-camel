package com.desabisc.guide.camel.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Processor for file processing that may fail
 */
class FileProcessingProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessingProcessor.class);
    private final Random random = new Random();
    
    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = exchange.getIn().getHeader("CamelFileName", String.class);
        String fileContent = exchange.getIn().getBody(String.class);
        
        logger.info("FileProcessingProcessor: Processing file: {}", fileName);
        
        // Simulate processing failure for files containing "error"
        if (fileContent != null && fileContent.toLowerCase().contains("error")) {
            throw new RuntimeException("File contains error content: " + fileName);
        }
        
        // Randomly fail 30% of the time
        if (random.nextDouble() < 0.3) {
            throw new RuntimeException("Random processing failure for file: " + fileName);
        }
        
        // Process the file content
        String processedContent = "PROCESSED: " + fileContent + "\nProcessed at: " + new java.util.Date();
        exchange.getIn().setBody(processedContent);
        
        logger.info("FileProcessingProcessor: File processed successfully: {}", fileName);
    }
}