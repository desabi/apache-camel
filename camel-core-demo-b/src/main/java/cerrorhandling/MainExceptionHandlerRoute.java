package cerrorhandling;

import org.apache.camel.builder.RouteBuilder;

public abstract class MainExceptionHandlerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // applicable on any route that extends this class
        onException(Exception.class)
                .log("Exception: ${exception.message}")
                .maximumRedeliveries(2)
                .redeliveryDelay(300);
        //.handled(true)
        //.continued(true);

        errorHandler(defaultErrorHandler());
    }
}
