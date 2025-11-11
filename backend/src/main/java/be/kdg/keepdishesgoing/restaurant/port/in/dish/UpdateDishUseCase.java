package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

import java.util.List;


public interface UpdateDishUseCase {
    
    Dish updateDish(UpdateDishCommand updateDishCommand);
    List<Dish> acceptAllChanges();
    
}
