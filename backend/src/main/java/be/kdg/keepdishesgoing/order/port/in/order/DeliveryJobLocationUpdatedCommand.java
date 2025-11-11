package be.kdg.keepdishesgoing.order.port.in.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryJobLocationUpdatedCommand (
        UUID eventId,
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId,
        double lat,
        double lng
){
}
