package cerrorhandling;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class ErrorHandlingApp {
    public static void main(String[] args) {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            //camelContext.addRoutes(new ErrorHandlerRoute());
            camelContext.addRoutes(new MainExceptionHandlingConfig());
            camelContext.addRoutes(new OnExceptionExampleRouteC());

            camelContext.start();

            Thread.sleep(TimeUnit.SECONDS.toMillis(2));

            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
