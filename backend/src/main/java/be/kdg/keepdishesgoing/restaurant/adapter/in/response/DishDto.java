package be.kdg.keepdishesgoing.restaurant.adapter.in.response;

import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import be.kdg.keepdishesgoing.restaurant.domain.FoodTag;
import be.kdg.keepdishesgoing.restaurant.domain.TypeOfDish;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DishDto(
        UUID dishId,
        String          name,
        TypeOfDish typeOfDish,
        List<FoodTag>  foodTagList,
        String          description,
        BigDecimal price,
        String          picture,
        DishStatus dishStatus
) {
}
