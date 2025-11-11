package be.kdg.keepdishesgoing.order.port.in.dish;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DishPublishedCommand (
        UUID dishId,
        String name,
        String typeOfDish,
        List<String> foodTags,
        String description,
        BigDecimal price,
        String picture,
        String dishStatus,
        UUID foodMenuId,
        UUID restaurantId
){
}
