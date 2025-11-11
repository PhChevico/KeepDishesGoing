package be.kdg.keepdishesgoing.order.port.in.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderReadyForPickupEventCommand(
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId
) {
}
