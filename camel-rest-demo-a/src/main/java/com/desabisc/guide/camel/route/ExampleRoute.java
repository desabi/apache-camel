package com.desabisc.guide.camel.route;

import com.desabisc.guide.camel.entity.Pet;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class ExampleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration()
                //.component("jetty")
                //.endpointProperty("handlers", "#securityHandler") // add it to te camel registry
                //.componentProperty()
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/petstore/api")
                .port(8080)
                .apiContextPath("/api-doc")
                .apiProperty("petstore.api", "Petstore API").apiProperty("api.version", "1.0");
        onException(JsonParseException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                .setBody().constant("Invalid json data");

        rest("/pet")
                .description("Pet Rest Service")
                .consumes("application/json")
                .produces("application/json")
                .post()
                    .id("createPet")
                    .description("Create a new pet")
                    .type(Pet.class)
                    .to("direct:createPet")
                .get("/{id}")
                    .id("getPet")
                    .description("Get pet by id")
                    .outType(Pet.class)
                    .to("direct:getPet")
                .put()
                    .id("updatePet")
                    .description("Update Pet")
                    .type(Pet.class)
                    .to("direct:updatePet")
                .delete("/{id}")
                    .id("deletePet")
                    .description("Delete Pet by id")
                    .to("direct:deletePet")
                .get()
                    .id("getAllPets")
                    .description("Get all pets")
                    .outType(Pet.class)
                    .to("direct:getAllPets");

        from("direct:createPet")
                .log("Received createPet request: ${body}")
                .bean("examplePetStorage", "createPet(${body})");

        from("direct:getPet")
                .log("Received getPet request: ${header.id}")
                .bean("examplePetStorage", "getPet(${header.id})")
                .choice()
                    .when(simple("${body} == null"))
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                        .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
                        .setBody().constant("No pet with such ID");

        from("direct:updatePet")
                .log("Received updatePet request: ${body}")
                .bean("examplePetStorage", "updatePet(${body})");


        from("direct:deletePet")
                .log("Received deletePet request: ${header.id}")
                .bean("examplePetStorage", "deletePet(${header.id})");

        from("direct:getAllPets")
                .log("Received getAllPets request")
                .bean("examplePetStorage", "getAllPets")
                .log("Message Body after getAllPets: ${body}");
    }
}
