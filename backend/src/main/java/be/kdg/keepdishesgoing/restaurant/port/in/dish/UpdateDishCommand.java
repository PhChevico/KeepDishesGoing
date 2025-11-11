package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import be.kdg.keepdishesgoing.restaurant.domain.TypeOfDish;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateDishCommand (
        UUID            dishId,
        String          name,
        TypeOfDish typeOfDish,
        List<String>    foodTags,
        String          description,
        BigDecimal price,
        String          picture,
        DishStatus dishStatus,
        UUID            restaurantId
){
}
