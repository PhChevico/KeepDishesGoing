package be.kdg.keepdishesgoing.common.events;

import be.kdg.keepdishesgoing.common.events.order.OrderAcceptedEvent;
import be.kdg.keepdishesgoing.common.events.order.OrderRefuseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

    @EventListener
    public void handleOrderAcceptedEvent(OrderAcceptedEvent event) {
        System.out.println("OrderAcceptedEvent received: " + event);
    }
    
    @EventListener
    public void handleOrderRefuseEvent(OrderRefuseEvent event) {
        System.out.println("OrderRefuseEvent received: " + event);
    }
}

