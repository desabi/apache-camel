package com.desabisc.guide.camel.dataformat.ega;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.ProducerTemplate;

public class JacksonTransformExample {

    public static void main(String[] args) throws Exception {
        // Create a new Camel context
        CamelContext context = new DefaultCamelContext();
        
        // Add our route
        context.addRoutes(new JacksonTransformRoute());
        
        // Start the Camel context
        context.start();
        
        // Create a producer template
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        
        // Create a Person object to marshal
        Person person = new Person("John", "Doe", 30);
        
        // Send the Person object to the marshal route
        System.out.println("Sending Person object to marshal route...");
        producerTemplate.sendBody("direct:marshal", person);
        
        // Wait a bit for the file to be created
        Thread.sleep(2000);
        
        // Read the JSON file and send it to unmarshal route
        String jsonContent = "{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"age\":25}";
        System.out.println("Sending JSON to unmarshal route...");
        producerTemplate.sendBody("direct:unmarshal", jsonContent);
        
        // Let's run for a while before terminating
        Thread.sleep(3000);
        
        // Stop the context
        context.stop();
    }
}
