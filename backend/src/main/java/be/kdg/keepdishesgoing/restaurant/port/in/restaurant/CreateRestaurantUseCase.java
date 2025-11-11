package be.kdg.keepdishesgoing.restaurant.port.in.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.exceptions.RestaurantOwnerNotFound;

public interface CreateRestaurantUseCase {
    
    Restaurant createRestaurant(CreateRestaurantCommand command) throws RestaurantOwnerNotFound;
    
}
