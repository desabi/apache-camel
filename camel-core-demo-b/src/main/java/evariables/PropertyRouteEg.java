package evariables;

import org.apache.camel.builder.RouteBuilder;

public class PropertyRouteEg extends RouteBuilder {

    public static final String ROUTE_ID = "propertyExampleRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .routeId(ROUTE_ID)
                // handle properties
                .setProperty("myProperty", constant("My Property Value"))
                .process(exchange -> {
                    exchange.getProperty("myProperty", String.class);
                    exchange.setProperty("myProperty2", "My Property Value2");

                })
                .transform(simple("Transformed body: ${exchangeProperty.myProperty}"))
                .log("${body}");

    }
}
