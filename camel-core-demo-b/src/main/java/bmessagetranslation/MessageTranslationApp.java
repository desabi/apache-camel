package bmessagetranslation;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class MessageTranslationApp {
    public static void main(String[] args) {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new ProcessorExampleRoute());
            camelContext.addRoutes(new BeanExampleRoute());
            camelContext.addRoutes(new TransformExampleRoute());

            camelContext.getRegistry().bind("my-transformer", new MyExampleMapper());

            camelContext.start();

            Thread.sleep(TimeUnit.SECONDS.toMillis(10));

            camelContext.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
