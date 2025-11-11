package be.kdg.keepdishesgoing.restaurant.adapter.in.listener;

import be.kdg.keepdishesgoing.common.config.RabbitMQTopology;
import be.kdg.keepdishesgoing.common.events.order.DeliveryJobLocationUpdatedEvent;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPickedUpEventCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPublishedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPickedUpListener {


    private final Logger log = LoggerFactory.getLogger(OrderPickedUpListener.class);
    private final OrderPublishedPort orderPublishedPort;


    public OrderPickedUpListener(OrderPublishedPort orderPublishedPort) {
        this.orderPublishedPort = orderPublishedPort;
    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_LOCATION_QUEUE)
    public void orderLocationUpdated(DeliveryJobLocationUpdatedEvent event) {
        log.info("ORDER PICKED UP FROM RESTAURANT");

        try{
            orderPublishedPort.project(new OrderPickedUpEventCommand(
                    event.restaurantId(),
                    event.orderId()
            ));
        } catch (Exception e){
            log.debug("Ignoring location update for order {}: {}", event.orderId(), e.getMessage());
        }
    }
}
