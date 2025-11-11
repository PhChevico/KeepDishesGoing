package be.kdg.keepdishesgoing.restaurant.port.in.dish;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateDishCommand(
        String name,
        String typeOfDish,
        List<String> foodTags,
        String description,
        BigDecimal price,
        String picture,
        String dishStatus,
        UUID restaurantId
) {
}
