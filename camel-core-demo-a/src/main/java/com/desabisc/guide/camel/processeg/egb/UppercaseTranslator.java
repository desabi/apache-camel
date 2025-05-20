package com.desabisc.guide.camel.processeg.egb;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * A simple Message Translator that converts message content to uppercase
 */
public class UppercaseTranslator implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        // Get the message body as a String
        String originalMessage = exchange.getIn().getBody(String.class);
        
        if (originalMessage != null) {
            // Transform the message to uppercase
            String translatedMessage = originalMessage.toUpperCase();
            
            // Set the transformed message back to the exchange
            exchange.getIn().setBody(translatedMessage);
        }
    }
}