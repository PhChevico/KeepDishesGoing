package be.kdg.keepdishesgoing.order.port.in.order;

import java.util.UUID;

public record OrderDeliveredEventCommand(
        UUID restaurantId,
        UUID orderId
) {
}
