package be.kdg.keepdishesgoing.restaurant.port.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;

public interface DishEventUnpublishPort {

    void unpublishDishEvents(Dish dish);


}
