package be.kdg.keepdishesgoing.restaurant.port.out.foodMenu;

import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;

public interface PersistFoodMenuPort {
    
    FoodMenu save(FoodMenu foodMenu);
    
}
