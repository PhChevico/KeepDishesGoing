package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.List;
import java.util.UUID;

public interface FindFoodMenuUseCase {
    
    List<Dish> findDishesByFoodMenu(FoodMenuId foodMenuId);
    Dish findDishById(UUID dishId);
    FoodMenu findFoodMenuByRestaurant(RestaurantId restaurantId);

}
