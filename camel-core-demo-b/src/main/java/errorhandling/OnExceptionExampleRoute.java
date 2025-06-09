package errorhandling;

import org.apache.camel.builder.RouteBuilder;

public class OnExceptionExampleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:ticker?period=1s")
                .onException(Exception.class)
                    .log("Exception: ${exception.message}")
                    .maximumRedeliveries(2)
                    .redeliveryDelay(300)
                    //.handled(true)
                    .continued(true)
                .end()
                .setBody(simple("Hello World"))
                .process(exchange -> {
                    throw new RuntimeException("Test Example");
                })
                .log("Ended route with body: ${body}");
    }
}
