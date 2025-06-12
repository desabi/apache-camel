package com.desabi.guide.camel.spring.configuration;

import jakarta.jms.ConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.language.bean.Bean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

// config for several connection factories with artemis
@Configuration
public class CamelConfiguration {

    @Bean
    public JmsComponent artemis(@Qualifier("artemis") ConnectionFactory connectionFactory) {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        return jmsComponent;
    }

}
