package be.kdg.keepdishesgoing.order.port.in.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRefusedEventCommand(
        UUID eventId,
        UUID orderId,
        String message,
        LocalDateTime occurredAt
) {
}
