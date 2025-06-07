package acamelrouting;

import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipientListExampleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:recipient.list.queue")
                .process(exchange -> {
                    // extract departments header
                    Object departments = exchange.getMessage().getHeader("departments");

                    // to create jms endpoints
                    List<String> whereToSend = new ArrayList<>();

                    if (departments != null) {
                        List<String> deparmentList = Arrays.asList(departments.toString().split(","));
                        // creates endpoints based on each department
                        deparmentList.forEach(department -> whereToSend.add("jms:"+department+".queue"));
                    }

                    exchange.getMessage().setHeader("whereToSend", whereToSend);
                })
                .recipientList(header("whereToSend"));
    }
}
