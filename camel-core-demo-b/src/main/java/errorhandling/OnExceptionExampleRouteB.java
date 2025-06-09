package errorhandling;

import org.apache.camel.builder.RouteBuilder;

public class OnExceptionExampleRouteB extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // applicable on any route in this class
        onException(Exception.class)
                .log("Exception: ${exception.message}")
                .maximumRedeliveries(2)
                .redeliveryDelay(300);
                //.handled(true)
                //.continued(true);

         errorHandler(defaultErrorHandler());

        from("timer:ticker_one?period=1s")
                .setBody(simple("Hello World"))
                .process(exchange -> {
                    throw new RuntimeException("Test Example");
                })
                .log("Ended route one with body: ${body}");

        from("timer:ticker_two?period=1s")
                .setBody(simple("Hello World"))
                .process(exchange -> {
                    throw new RuntimeException("Test Example");
                })
                .log("Ended route two with body: ${body}");
    }
}
