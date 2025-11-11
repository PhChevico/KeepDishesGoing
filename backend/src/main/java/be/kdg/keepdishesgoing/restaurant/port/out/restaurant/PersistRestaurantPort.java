package be.kdg.keepdishesgoing.restaurant.port.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;

public interface PersistRestaurantPort {
    
    Restaurant save(Restaurant restaurant);
    
}
