package cerrorhandling;

import org.apache.camel.builder.RouteConfigurationBuilder;

public class MainExceptionHandlingConfig extends RouteConfigurationBuilder {
    @Override
    public void configuration() throws Exception {

        routeConfiguration("example-error-handling").

        // applicable on any route
        onException(Exception.class)
                .log("Exception: ${exception.message}")
                .maximumRedeliveries(2)
                .redeliveryDelay(300);
                //.handled(true)
                //.continued(true);


    }
}
