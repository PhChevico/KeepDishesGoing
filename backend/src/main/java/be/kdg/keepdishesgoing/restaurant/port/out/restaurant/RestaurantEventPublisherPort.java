package be.kdg.keepdishesgoing.restaurant.port.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;

public interface RestaurantEventPublisherPort {
    
    void publishDomainEvents(Restaurant restaurant);
    
}
