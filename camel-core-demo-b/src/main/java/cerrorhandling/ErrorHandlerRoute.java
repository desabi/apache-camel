package cerrorhandling;

import org.apache.camel.builder.RouteBuilder;

public class ErrorHandlerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:ticker?period=1s")
                // first option
                //.errorHandler(defaultErrorHandler()) default configuration
                //.errorHandler(defaultErrorHandler().maximumRedeliveries(3).redeliveryDelay(500)) config retries
                //.errorHandler(deadLetterChannel("file:src/main/resources/files/error?fileName=example-error.txt").maximumRedeliveries(3))

                // second option: for a specific step, like a processor
                /*
                .setBody(simple("Hello World"))
                .doTry()
                    .process(exchange -> {
                        throw new RuntimeException("Test Example");
                    })
                .doCatch(Exception.class).onWhen(simple("${exception.message} contains 'Example'"))
                    .log("Catch Exception: ${exception}")
                .doFinally()
                    .log("Finally: ${exception}")
                .end()*/

                // third option: for the whole route
                // onException block

                .log("Ended route with body: ${body}");
    }
}
