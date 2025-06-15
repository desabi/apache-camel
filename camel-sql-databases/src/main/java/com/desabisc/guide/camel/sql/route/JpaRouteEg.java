package com.desabisc.guide.camel.sql.route;

import com.desabisc.guide.camel.sql.entity.Pet;
import org.apache.camel.builder.RouteBuilder;

import java.time.LocalDate;

public class JpaRouteEg extends RouteBuilder {
    public static String ROUTE_ID_SELECT = "JPAExampleRouteSelect";
    public static String ROUTE_ID_INSERT = "JPAExampleRouteInsert";

    @Override
    public void configure() {
        from("timer:tick?period=90s")
                .routeId(ROUTE_ID_INSERT)
                .process(exchange -> {
                    Pet pet = new Pet();
                    pet.setName("doggie");
                    pet.setStatus("Available");
                    pet.setType(Pet.PetType.Dog);
                    pet.setBirthDate(LocalDate.now());
                    exchange.getMessage().setBody(pet);
                })
                .to("jpa://com.desabisc.guide.camel.sql.entity.Pet");

        from("timer:tick?period=5s")
                .routeId(ROUTE_ID_SELECT)
                .setHeader("petName", constant("doggie"))
                .toD("jpa://com.desabisc.guide.camel.sql.entity.Pet" +
                        "?query=select p from com.desabisc.guide.camel.sql.entity.Pet p " +
                        "where p.name='${header.petName}'")
                .log("SELECT RESULT: ${body}");
    }
}
