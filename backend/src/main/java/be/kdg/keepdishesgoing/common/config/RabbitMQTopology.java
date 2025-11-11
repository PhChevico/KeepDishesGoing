package be.kdg.keepdishesgoing.common.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQTopology {

    public static final String DELIVERY_EXCHANGE = "kdg.events";

    // Queues for events we publish (delivery service consumes from us)
    public static final String ORDER_ACCEPTED_QUEUE = "restaurant.order.accepted";
    public static final String ORDER_READY_QUEUE = "restaurant.order.ready";

    // Queues for events we consume (delivery service publishes to us)
    public static final String ORDER_PICKED_UP_QUEUE = "delivery.order.pickedup";
    public static final String ORDER_DELIVERED_QUEUE = "delivery.order.delivered";
    public static final String ORDER_LOCATION_QUEUE = "delivery.order.location";

    // Topic exchange
    @Bean
    TopicExchange deliveryExchange() {
        return ExchangeBuilder
                .topicExchange(DELIVERY_EXCHANGE)
                .durable(true)
                .build();
    }

    // Queues
    @Bean
    Queue orderAcceptedQueue() {
        return QueueBuilder.durable(ORDER_ACCEPTED_QUEUE).build();
    }

    @Bean
    Queue orderReadyQueue() {
        return QueueBuilder.durable(ORDER_READY_QUEUE).build();
    }

    @Bean
    Queue orderPickedUpQueue() {
        return QueueBuilder.durable(ORDER_PICKED_UP_QUEUE).build();
    }

    @Bean
    Queue orderDeliveredQueue() {
        return QueueBuilder.durable(ORDER_DELIVERED_QUEUE).build();
    }

    @Bean
    Queue orderLocationQueue() {
        return QueueBuilder.durable(ORDER_LOCATION_QUEUE).build();
    }

    // Bindings (we publish to delivery service)
    @Bean
    Binding bindOrderAcceptedToExchange() {
        return BindingBuilder
                .bind(orderAcceptedQueue())
                .to(deliveryExchange())
                .with("restaurant.*.order.accepted.v1");
    }

    @Bean
    Binding bindOrderReadyToExchange() {
        return BindingBuilder
                .bind(orderReadyQueue())
                .to(deliveryExchange())
                .with("restaurant.*.order.ready.v1");
    }

    // Bindings (we consume from delivery service)
    @Bean
    Binding bindOrderPickedUpToExchange() {
        return BindingBuilder
                .bind(orderPickedUpQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.pickedup.v1");
    }

    @Bean
    Binding bindOrderDeliveredToExchange() {
        return BindingBuilder
                .bind(orderDeliveredQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.delivered.v1");
    }

    @Bean
    Binding bindOrderLocationToExchange() {
        return BindingBuilder
                .bind(orderLocationQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.location.v1");
    }
}
