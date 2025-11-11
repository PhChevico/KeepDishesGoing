package be.kdg.keepdishesgoing.common.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import java.util.Map;


@Configuration
public class RabbitMQConfig {

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setMessageConverter(jackson2JsonMessageConverter());
        return simpleRabbitListenerContainerFactory;
    }

    @Bean
    MessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        // Map the publisher type to the consumer type
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*"); // or a specific package
        classMapper.setIdClassMapping(Map.of(
                "be.kdg.deliveryservice.domain.event.DeliveryDeliveredEvent",
                be.kdg.keepdishesgoing.common.events.order.OrderDeliveredEvent.class,
                "be.kdg.deliveryservice.domain.event.DeliveryJobLocationUpdatedEvent",
                be.kdg.keepdishesgoing.common.events.order.DeliveryJobLocationUpdatedEvent.class
        ));


        converter.setClassMapper(classMapper);
        return converter;
    }

}

