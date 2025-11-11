package be.kdg.keepdishesgoing.restaurant.adapter.in.listener;


import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.common.events.order.OrderPublishEvent;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPublishCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPublishedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPublishedListener {

    private final Logger log = LoggerFactory.getLogger(OrderPublishedListener.class);
    private final OrderPublishedPort orderPublishedPort;

    public OrderPublishedListener(OrderPublishedPort orderPublishedPort) {
        this.orderPublishedPort = orderPublishedPort;
    }
    
    @ApplicationModuleListener
    public void orderPublished(OrderPublishEvent orderPublishEvent) {
        log.info("Order sent: {}", orderPublishEvent);

        orderPublishedPort.project(new OrderPublishCommand(
                orderPublishEvent.orderId(),

                Address.of(orderPublishEvent.street(), 
                orderPublishEvent.number(), 
                orderPublishEvent.postalCode(), 
                orderPublishEvent.city(), 
                orderPublishEvent.country()), 
                
                orderPublishEvent.restaurantId(), 
                orderPublishEvent.orderLines(), 
                orderPublishEvent.totalPrice()
        ));
    }
}
