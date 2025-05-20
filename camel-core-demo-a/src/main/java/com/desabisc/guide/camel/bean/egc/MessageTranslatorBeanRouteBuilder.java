package com.desabisc.guide.camel.bean.egc;

import org.apache.camel.builder.RouteBuilder;

// Using the Message Translator EIP, using beans

public class MessageTranslatorBeanRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Define a route that reads from a file, processes it through our translator bean, and writes to another file
        from("file:src/data/input?noop=true")
            .log("Received message: ${body}")
            // Use the 'bean' EIP to invoke our translator
            .bean("messageTranslator", "toUpperCase")
            .log("Translated message: ${body}")
            .to("file:src/data/output");
            
        // TODO: this is a consumer, it requires that a producer triggers a call to this consumer
        // Alternative route using direct method reference to the bean
        from("direct:lowerCaseTranslator")
            .log("Received message for lowercase translation: ${body}")
            .bean(MessageTranslator.class, "toLowerCase")
            .log("Lowercase translated message: ${body}")
            .to("mock:result");

        // TODO: this is a consumer, it requires that a producer triggers a call to this consumer
        // Using method selection
        from("direct:dynamicTranslator")
            .log("Received message for dynamic translation: ${body}")
            // Use header to specify which method to call
            .bean("messageTranslator", "method:${header.translationType}")
            .log("Dynamically translated message: ${body}")
            .to("mock:result");

        // TODO: this is a consumer, it requires that a producer triggers a call to this consumer
        // Using bean as part of a content-based router
        from("direct:conditionalTranslate")
            .choice()
                .when(header("translationType").isEqualTo("uppercase"))
                    .bean("messageTranslator", "toUpperCase")
                .when(header("translationType").isEqualTo("lowercase"))
                    .bean("messageTranslator", "toLowerCase")
                .when(header("translationType").isEqualTo("reverse"))
                    .bean("messageTranslator", "reverse")
                .otherwise()
                    .log("No translation specified, keeping original message")
            .end()
            .log("Final message after conditional translation: ${body}")
            .to("mock:result");
    }
}