package bmessagetranslation;

import org.apache.camel.builder.RouteBuilder;

public class BeanExampleRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/files?fileName=processor-bean-example.json&noop=true")
                .unmarshal().json()
                .log("Body: ${body}, Headers: ${headers}")
                // calls the only method from the class, it searches automatically for the exchange parameter
                .bean("my-transformer")
                .marshal().json()
                .to("file:src/main/resources/files/output?fileName=processor-bean-example.json");
    }
}
