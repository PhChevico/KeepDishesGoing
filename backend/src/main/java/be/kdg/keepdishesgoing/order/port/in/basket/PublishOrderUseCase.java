package be.kdg.keepdishesgoing.order.port.in.basket;

import be.kdg.keepdishesgoing.order.domain.Order;

public interface PublishOrderUseCase {
    
    Order publishOrder(PublishOrderCommand publishOrderCommand);
    
}
