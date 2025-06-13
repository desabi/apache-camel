package com.desabi.guide.camel.soap.configuration;

import java.util.HashMap;
import java.util.Map;

import com.desabi.guide.camel.soap.petservice.PetServive;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfiguration {
    @Bean
    CxfEndpoint petstoreFromWSDL() {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setWsdlURL("wsdl/PetService.wsdl");
        endpoint.setAddress("/pets");
        endpoint.setServiceClass(PetService.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put("schema-validation-enabled", "true");
        endpoint.setProperties(properties);
        return endpoint;
    }

    @Bean
    CxfEndpoint petstoreFromJava() {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setAddress("/pets");
        endpoint.setServiceClass(com.desabi.guide.camel.soap.service.PetService.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put("schema-validation-enabled", "true");
        endpoint.setProperties(properties);
        return endpoint;
    }
}