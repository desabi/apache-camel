package ftesting;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class TestingApplication {
    public static void main(String[] args) {
        try (CamelContext camelContext = new DefaultCamelContext()) {

            camelContext.addRoutes(new ExampleRoute());

            camelContext.start();
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
