package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

import java.util.List;
import java.util.UUID;

public interface FindDishesUseCase {
    
    List<Dish> findDishesByRestaurantId(UUID restaurantId);
}
