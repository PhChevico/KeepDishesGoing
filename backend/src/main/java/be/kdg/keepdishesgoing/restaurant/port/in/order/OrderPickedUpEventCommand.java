package be.kdg.keepdishesgoing.restaurant.port.in.order;

import java.util.UUID;

public record OrderPickedUpEventCommand(
        UUID restaurantId,
        UUID orderId
) {
}
