package be.kdg.keepdishesgoing.restaurant.port.in.order;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.common.events.order.OrderPublishEvent;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderPublishCommand(
        UUID orderId,
        Address deliveryAddress,
        UUID restaurantId,
        List<OrderPublishEvent.OrderLine> orderLines,
        BigDecimal totalPrice
) {
}
