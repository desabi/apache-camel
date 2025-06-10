package dbeans;

import org.apache.camel.Exchange;

public class ExampleBean {

    // automatic method binding with Exchange
    /*public void doStuff(Exchange exchange) {
        String body = exchange.getMessage().getBody(String.class);
        String bodyUppercase = body.toUpperCase();
        exchange.getMessage().setBody(bodyUppercase);
    }*/

    public String doStuff(Exchange exchange) {
        return exchange.getMessage().getBody(String.class).toUpperCase();
    }
}
