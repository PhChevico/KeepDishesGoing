package be.kdg.keepdishesgoing.restaurant.port.in.order;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;

public interface OrderEventPublisherPort {
    
    void publishDomainEvent(RestaurantOrder restaurantOrder);
}
