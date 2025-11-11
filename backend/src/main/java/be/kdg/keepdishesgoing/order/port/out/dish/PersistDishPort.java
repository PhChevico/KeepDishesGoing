package be.kdg.keepdishesgoing.order.port.out.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;

public interface PersistDishPort {
    
    DishProjection save(DishProjection dishProjection);
    
}
