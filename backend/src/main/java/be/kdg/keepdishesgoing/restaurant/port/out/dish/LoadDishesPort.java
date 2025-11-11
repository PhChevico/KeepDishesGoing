package be.kdg.keepdishesgoing.restaurant.port.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

import java.util.List;
import java.util.UUID;

public interface LoadDishesPort {
    
    List<Dish> loadDishesByMenu(UUID foodMenuId);
    Dish loadDishById(UUID dishId);
    List<Dish> loadAllDishesDraft();
    List<Dish> loadAllDishesByRestaurantId(UUID restaurantId);
}
