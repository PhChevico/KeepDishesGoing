package be.kdg.keepdishesgoing.order.port.in.dish;

import java.util.UUID;

public record AddDishToBasketCommand(
        UUID basketId,
        UUID dishId,
        String dishName,
        int quantity
) {
}
