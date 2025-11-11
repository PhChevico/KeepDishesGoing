package be.kdg.keepdishesgoing.order.adapter.in.listener;

import be.kdg.keepdishesgoing.common.config.RabbitMQTopology;
import be.kdg.keepdishesgoing.common.events.order.*;
import be.kdg.keepdishesgoing.order.port.in.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);
    private final OrderUpdateEventPort orderUpdateEventPort;
    
    public OrderEventListener(OrderUpdateEventPort orderUpdateEventPort) {
        this.orderUpdateEventPort = orderUpdateEventPort;
    }
    
    @ApplicationModuleListener
    public void orderAccepted(OrderAcceptedEvent orderAcceptedEvent) {
        orderUpdateEventPort.project(new OrderAcceptedEventCommand(
                orderAcceptedEvent.occurredAt(),
                orderAcceptedEvent.restaurantId(),
                orderAcceptedEvent.orderId()));
    }
    
    @ApplicationModuleListener
    public void orderRefused(OrderRefuseEvent orderRefuseEvent) {
        orderUpdateEventPort.project(new OrderRefusedEventCommand(
                orderRefuseEvent.eventId(),
                orderRefuseEvent.orderId(),
                orderRefuseEvent.message(),
                orderRefuseEvent.occurredAt()
        ));
    }

    @ApplicationModuleListener
    public void orderReadyForPickup(OrderReadyForPickupEvent event) {
        orderUpdateEventPort.project(new OrderReadyForPickupEventCommand(
                event.occurredAt(),
                event.restaurantId(),
                event.orderId()
        ));
    }
//    
//    @RabbitListener(queues = RabbitMQTopology.ORDER_PICKED_UP_QUEUE)
//    public void orderPickedUp(OrderPickedUpEvent event) {
//        log.info("ORDER PICKED UP!!!!!");
//
//        orderUpdateEventPort.project(new OrderPickedUpEventCommand(
//                event.restaurantId(),
//                event.orderId()
//        ));
//    }    

    @RabbitListener(queues = RabbitMQTopology.ORDER_LOCATION_QUEUE)
    public void orderLocationUpdated(DeliveryJobLocationUpdatedEvent event) {
        log.info("LOCATION UPDATE: order={} lat={} lng={}", event.orderId(), event.lat(), event.lng());

        try{
            orderUpdateEventPort.project(new OrderPickedUpEventCommand(
                    event.restaurantId(),
                    event.orderId()
            ));
        } catch (Exception e){
            log.debug("Ignoring location update for order {}: {}", event.orderId(), e.getMessage());
        }
        
    }



    @RabbitListener(queues = RabbitMQTopology.ORDER_DELIVERED_QUEUE)
    public void orderDelivered(OrderDeliveredEvent event) {
        try {
            orderUpdateEventPort.project(new OrderDeliveredEventCommand(
                    event.restaurantId(),
                    event.orderId()
            ));
            log.info("ORDER DELIVERED!!!!! {}", event);
        } catch (IllegalStateException e) {
            log.warn("Cannot mark order {} as delivered: {}", event.orderId(), e.getMessage());
        }
    }
    
}
