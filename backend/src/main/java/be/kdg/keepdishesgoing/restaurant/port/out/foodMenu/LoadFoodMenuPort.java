package be.kdg.keepdishesgoing.restaurant.port.out.foodMenu;

import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.Optional;

public interface LoadFoodMenuPort {
    
    Optional<FoodMenu> loadMenuByRestaurant(RestaurantId restaurantId);
    Optional<FoodMenu> loadMenuById(FoodMenuId foodMenuId);
    Optional<FoodMenu> loadMenuByWithDishes(FoodMenuId foodMenuId);
    
}
