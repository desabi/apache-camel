package com.desabisc.guide.camel.bean.ega;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class BeanComponentEgA {

  public static void main(String[] args) throws Exception {
    /*
    // Create a Camel Main instance
    Main main = new Main();

    // Register beans
    main.bind("messageGenerator", new MessageGenerator());

    // Add routes
    main.configure().addRoutesBuilder(new BeanRouteBuilder());

    System.out.println("Starting Camel application...");

    // Start the Camel application
    main.run();*/

    // Create a registry to register beans
    SimpleRegistry registry = new SimpleRegistry();
    registry.bind("messageGenerator", new MessageGenerator());

    // Create a CamelContext with the registry
    CamelContext context = new DefaultCamelContext(registry);

    // Add routes
    context.addRoutes(new BeanRouteBuilder());

    System.out.println("Starting Camel application...");

    // Start the Camel context
    context.start();

    // Example of manually triggering the direct route
    ProducerTemplate template = context.createProducerTemplate();
    template.sendBody("direct:processMessage", "test message");

    // Keep the application running
    Thread.sleep(30000); // Run for 30 seconds

    // Shutdown the context
    context.stop();
  }

  // Our custom RouteBuilder using the bean component
  public static class BeanRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
      // Using timer as a trigger and then calling the bean
      // Uses the MessageGenerator.generateMessage() method
      from("timer:beanTimer?period=5000")
          .to("bean:messageGenerator?method=generateMessage")
          .log(">>> ${body}");

      // Another example: Direct route that calls a bean
      // Used with ProducerTemplate for manually triggering
      // Uses the MessageGenerator.processMessage() method
      from("direct:processMessage")
          .to("bean:messageGenerator?method=processMessage")
          .log("Processed message: ${body}");
    }
  }

}
