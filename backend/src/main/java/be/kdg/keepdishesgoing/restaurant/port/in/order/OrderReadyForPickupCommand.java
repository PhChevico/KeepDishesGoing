package be.kdg.keepdishesgoing.restaurant.port.in.order;

import java.util.UUID;

public record OrderReadyForPickupCommand (
        UUID orderId
) {
}
