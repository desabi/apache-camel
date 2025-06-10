package evariables;

import org.apache.camel.builder.RouteBuilder;

public class VariableCustomScopeRoute extends RouteBuilder {

    public static final String ROUTE_ID = "variableCustomScopeRoute";

    @Override
    public void configure() throws Exception {
        from("timer:tick?period=3s")
                .routeId(ROUTE_ID)
                .setVariable(VariableRepositoryEg.ID + ":myVariable", constant("My Variable Value"))
                .log("Value for variable." + VariableRepositoryEg.ID + ":myVariable : " +
                        "${variable." + VariableRepositoryEg.ID + ":myVariable}");
    }
}
