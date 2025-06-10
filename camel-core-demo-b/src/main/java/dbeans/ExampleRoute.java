package dbeans;

import org.apache.camel.builder.RouteBuilder;

public class ExampleRoute extends RouteBuilder {

    private static final String ROUTE_ID = "exampleRoute";

    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/files/input?noop=true")
                .routeId(ROUTE_ID)
                //.bean("exampleBean") // calls the doStuff method by default

                .bean("exampleBean", "doStuff")

                //.bean("bean:exampleBean?method=doStuff")

                //.choice()
                //.when().method("exampleBean", "doStuff")

                //.filter().method("exampleBean", "doStuff")

                .to("file:src/main/resources/files/output");
    }
}
