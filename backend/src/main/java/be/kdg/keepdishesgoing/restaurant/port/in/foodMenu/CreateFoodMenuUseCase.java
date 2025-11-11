package be.kdg.keepdishesgoing.restaurant.port.in.foodMenu;

import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.exceptions.RestaurantOwnerNotFound;

public interface CreateFoodMenuUseCase {
    
    FoodMenu createFoodMenu(CreateFoodMenuCommand createFoodMenuCommand) throws RestaurantOwnerNotFound;
    
}
