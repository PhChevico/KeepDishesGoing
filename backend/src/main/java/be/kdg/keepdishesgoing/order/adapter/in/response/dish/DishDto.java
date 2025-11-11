package be.kdg.keepdishesgoing.order.adapter.in.response.dish;

import be.kdg.keepdishesgoing.order.domain.DishStatusProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DishDto(
        UUID dish,
        String          name,
        TypeOfDishProjection typeOfDish,
        List<FoodTagProjection>  foodTagList,
        String          description,
        BigDecimal price,
        String          picture,
        DishStatusProjection dishStatus
) {
}
