package com.desabisc.guide.camel.dataformat.ega;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class JacksonTransformRoute extends RouteBuilder  {

  // Create Jackson data format for Person class
  JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Person.class);

  @Override
  public void configure() throws Exception {
    // Marshal route - Java object to JSON
    from("direct:marshal")
        .log("Before marshaling: ${body}")
        .marshal(jsonDataFormat)
        .log("After marshaling: ${body}")
        .to("file:src/data/output?fileName=person.json");

    // Unmarshal route - JSON to Java object
    from("direct:unmarshal")
        .log("Before unmarshalling: ${body}")
        .unmarshal(jsonDataFormat)
        .log("After unmarshalling: ${body}")
        .to("direct:processObject");

    // Route to process unmarshaled object
    from("direct:processObject")
        .log("Processing Person object: ${body.firstName} ${body.lastName}, age: ${body.age}");
  }
}
