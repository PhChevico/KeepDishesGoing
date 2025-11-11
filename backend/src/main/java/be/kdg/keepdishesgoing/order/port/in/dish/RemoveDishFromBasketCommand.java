package be.kdg.keepdishesgoing.order.port.in.dish;

import java.util.UUID;

public record RemoveDishFromBasketCommand(
        UUID dishId,
        UUID basketId,
        int quantity
) {
}
