package com.desabi.guide.camel.soap.route;

import com.desabi.guide.camel.soap.petservice.NoSuchPet;
import com.desabi.guide.camel.soap.petservice.NoSuchPetException;
import com.desabi.guide.camel.soap.petservice.Pet;
import com.desabi.guide.camel.soap.service.java2wsdl.PetService;
import java.util.List;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;

@Component
public class SoapServerFromWsdlRoute extends RouteBuilder {

    public static final String ID = "SoapServerFromWsdlRoute";

    private final PetService petService;

    public SoapServerFromWsdlRoute(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void configure() {
        from("cxf:bean:petstoreFromWSDL")
                .routeId(ID)
                .toD("direct:${header.operationName}");

        from("direct:getPetsByName")
                .process(exchange -> {
                    Message message = exchange.getMessage();
                    String name = message.getBody(String.class);
                    List<Pet> petsWithName = petService.getPetsByName(name);
                    if (petsWithName == null || petsWithName.isEmpty()) {
                        NoSuchPet noSuchPetMessage = new NoSuchPet();
                        noSuchPetMessage.setPetName(name);
                        throw new NoSuchPetException("Pet not found", noSuchPetMessage);
                    }

                    MessageContentsList result = new MessageContentsList();
                    result.add(petsWithName);
                    message.setBody(result);
                });

        from("direct:updatePet")
                .bean(petService, "updatePet(${body})");
    }
}