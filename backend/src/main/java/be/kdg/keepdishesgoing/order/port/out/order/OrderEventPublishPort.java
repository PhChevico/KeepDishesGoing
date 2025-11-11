package be.kdg.keepdishesgoing.order.port.out.order;

import be.kdg.keepdishesgoing.order.domain.Order;

public interface OrderEventPublishPort {
    
    void publishOrderEvent(Order order);
}
