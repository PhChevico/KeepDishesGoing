package be.kdg.keepdishesgoing.restaurant.adapter.in.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateDishRequest (
        String name,
        String typeOfDish,
        List<String> foodTags,
        String description,
        BigDecimal price,
        String picture,
        String dishStatus,
        UUID restaurantId
){
}
