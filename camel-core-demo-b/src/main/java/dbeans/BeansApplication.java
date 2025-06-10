package dbeans;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class BeansApplication {
    public static void main(String[] args) {
        try (CamelContext camelContext = new DefaultCamelContext()) {

            camelContext.addRoutes(new ExampleRoute());
            camelContext.addRoutes(new MethodBindingExampleRoute());

            camelContext.getRegistry().bind("exampleBean", new ExampleBean());
            camelContext.getRegistry().bind("methodBindingExampleBean", new MethodBindingExampleBean());

            camelContext.start();

            Thread.sleep(TimeUnit.SECONDS.toMillis(2));

            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
