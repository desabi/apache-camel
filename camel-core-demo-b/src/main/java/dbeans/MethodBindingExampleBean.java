package dbeans;

import org.apache.camel.Handler;

public class MethodBindingExampleBean {

    @Handler
    public String transform(String body) {
        return body.replace("hello", "bye");
    }
}
