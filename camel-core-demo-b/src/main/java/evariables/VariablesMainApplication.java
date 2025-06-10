package evariables;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class VariablesMainApplication {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            // handle properties with property
            //camelContext.addRoutes(new PropertyRouteEg());

            // handle variables
            //camelContext.addRoutes(new VariableRouteEg());

            // handle variable with route scope
            //camelContext.addRoutes(new VariableRouteScopeRoute());

            // handle variable with global scope
            //camelContext.addRoutes(new VariableGlobalScopeRoute());
            // set global variable
            //camelContext.setVariable("myVariable", "My Variable Value");

            // handle custom variable
            //camelContext.addRoutes(new VariableCustomScopeRoute());
            // add the variable repository to the camel context registry
            //camelContext.getRegistry().bind(VariableRepositoryEg.ID, new VariableRepositoryEg());

            // use variable receive
            //camelContext.addRoutes(new VariableReceiveEgRoute());

            // use variable send
            camelContext.addRoutes(new VariableSendRouteEg());

            camelContext.start();
            Thread.sleep(TimeUnit.SECONDS.toMillis(20));
        }
    }
}
