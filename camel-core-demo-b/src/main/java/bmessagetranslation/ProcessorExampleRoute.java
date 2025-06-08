package bmessagetranslation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.builder.RouteBuilder;

public class ProcessorExampleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/files?fileName=processor-example.json&noop=true")
                .unmarshal().json()
                .log("Body: ${body}, Headers: ${headers}")
                .process(exchange -> {
                    // extract file content as String
                    ObjectNode body = exchange.getMessage().getBody(ObjectNode.class);

                    // transform
                    // String transformedBody = body.toUpperCase();

                    // without unmarshal
                    /*ObjectMapper mapper = new ObjectMapper();
                    ObjectNode node = mapper.readValue(body, ObjectNode.class);
                    node.put("newField", "newValue");*/

                    // with unmarshall
                    body.put("newField", "newValue");

                    // set new transformed body to the exchange
                    //exchange.getMessage().setBody(transformedBody);
                    //exchange.getMessage().setBody(node.toPrettyString());
                    exchange.getMessage().setBody(body);
                })
                .marshal().json()
                .to("file:src/main/resources/files/output?fileName=processor-example.json");
    }
}
