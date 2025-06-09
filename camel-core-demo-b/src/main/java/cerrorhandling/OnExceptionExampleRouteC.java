package cerrorhandling;

import org.apache.camel.builder.RouteBuilder;

public class OnExceptionExampleRouteC extends RouteBuilder /*MainExceptionHandlerRoute*/ {

    @Override
    public void configure() throws Exception {

        //super.configure();

        from("timer:ticker_one?period=1s")
                .routeConfigurationId("example-error-handling")
                .setBody(simple("Hello World"))
                .process(exchange -> {
                    throw new RuntimeException("Test Example");
                })
                .log("Ended route one with body: ${body}");

        from("timer:ticker_two?period=1s")
                .routeConfigurationId("example-error-handling")
                .setBody(simple("Hello World"))
                .process(exchange -> {
                    throw new RuntimeException("Test Example");
                })
                .log("Ended route two with body: ${body}");
    }
}
