package com.desabi.guide.camel.spring.quarkus.jms;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.ConnectionFactory;
import jdk.jfr.Name;
import org.apache.camel.component.jms.JmsComponent;

@ApplicationScoped
public class JmsComponentProducer {

    @Name("artemis")
    public JmsComponent artemis(@Identifier("artemisMQFactory") ConnectionFactory connectionFactory) {
        JmsComponent result = new JmsComponent();
        result.setConnectionFactory(connectionFactory);
        return result;
    }

    @Name("rabbitmq")
    public JmsComponent rabbitmq(@Identifier("rabbitMQFactory") ConnectionFactory connectionFactory) {
        JmsComponent result = new JmsComponent();
        result.setConnectionFactory(connectionFactory);
        return result;
    }
}
