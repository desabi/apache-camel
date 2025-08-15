package seda.ega;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Route builder demonstrating SEDA (Staged Event-Driven Architecture) component SEDA provides
 * asynchronous processing using in-memory blocking queues
 */
public class SedaRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    // Route 1: Producer route - generates messages and sends to SEDA queue
    from("timer://messageGenerator?period=2000")
        .routeId("producer-route")
        .process(new MessageGenerator())
        .log("Sending message: ${body}")
        .to("seda:orderQueue?size=100&concurrentConsumers=3");

    // Route 2: Consumer route - processes messages from SEDA queue
    from("seda:orderQueue")
        .routeId("consumer-route")
        .log("Received message: ${body} on thread: ${threadName}")
        .process(new OrderProcessor())
        .to("seda:validationQueue");

    // Route 3: Validation route - validates processed orders
    from("seda:validationQueue?concurrentConsumers=2")
        .routeId("validation-route")
        .log("Validating order: ${body} on thread: ${threadName}")
        .process(new ValidationProcessor())
        .choice()
        .when(header("valid").isEqualTo(true))
        .log("Order validated successfully: ${body}")
        .to("seda:fulfillmentQueue")
        .otherwise()
        .log("Order validation failed: ${body}")
        .to("seda:errorQueue");

    // Route 4: Fulfillment route
    from("seda:fulfillmentQueue")
        .routeId("fulfillment-route")
        .log("Processing fulfillment for: ${body} on thread: ${threadName}")
        .process(new FulfillmentProcessor());

    // Route 5: Error handling route
    from("seda:errorQueue")
        .routeId("error-route")
        .log("Handling error for: ${body} on thread: ${threadName}")
        .process(new ErrorProcessor());
  }
}