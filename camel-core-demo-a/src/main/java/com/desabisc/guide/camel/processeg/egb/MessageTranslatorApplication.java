package com.desabisc.guide.camel.processeg.egb;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MessageTranslatorApplication {
    public static void main(String[] args) throws Exception {
        // Create a CamelContext
        CamelContext context = new DefaultCamelContext();

        // Add routes
        context.addRoutes(new MessageTranslatorRouteBuilder());

        // Start the Camel context
        context.start();

        // Keep the application running
        Thread.sleep(20000); // Run for 20 seconds

        // Shutdown the context
        context.stop();
    }
}