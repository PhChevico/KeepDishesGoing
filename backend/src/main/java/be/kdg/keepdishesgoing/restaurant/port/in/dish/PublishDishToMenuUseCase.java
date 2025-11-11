package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

public interface PublishDishToMenuUseCase {
    
    Dish publishDishToFoodMenu(PublishDishToMenuCommand publishDishToMenuCommand);
    
}
