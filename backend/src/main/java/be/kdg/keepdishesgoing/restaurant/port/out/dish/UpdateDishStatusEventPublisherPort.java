package be.kdg.keepdishesgoing.restaurant.port.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

public interface UpdateDishStatusEventPublisherPort {
    
    void markDishOutOfStockEvent(Dish dish);
    void markDishInStockEvent(Dish dish);
}
