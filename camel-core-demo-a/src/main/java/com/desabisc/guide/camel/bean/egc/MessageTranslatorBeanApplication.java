package com.desabisc.guide.camel.bean.egc;

import com.desabisc.guide.camel.processeg.egb.MessageTranslatorRouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class MessageTranslatorBeanApplication {

    public static void main(String[] args) throws Exception {
        // Create a registry to register beans
        SimpleRegistry registry = new SimpleRegistry();
        registry.bind("messageTranslator", new MessageTranslator());

        // Create a CamelContext
        CamelContext context = new DefaultCamelContext(registry);

        // Add routes
        context.addRoutes(new MessageTranslatorBeanRouteBuilder());

        // Start the Camel context
        context.start();

        // Keep the application running
        Thread.sleep(20000); // Run for 20 seconds

        // Shutdown the context
        context.stop();

    }
}