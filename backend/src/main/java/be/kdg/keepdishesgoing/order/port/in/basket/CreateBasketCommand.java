package be.kdg.keepdishesgoing.order.port.in.basket;

import java.util.UUID;

public record CreateBasketCommand(
        UUID restaurantId,
        UUID anonymousId

) {
}
