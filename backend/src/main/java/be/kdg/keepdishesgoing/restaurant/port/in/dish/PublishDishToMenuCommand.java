package be.kdg.keepdishesgoing.restaurant.port.in.dish;

import java.util.UUID;

public record PublishDishToMenuCommand(
        UUID dishId,
        UUID menuId
) {
}
