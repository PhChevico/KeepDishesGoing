package be.kdg.keepdishesgoing.restaurant.adapter.in.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import be.kdg.keepdishesgoing.restaurant.domain.OrderLine;
import be.kdg.keepdishesgoing.common.events.order.Address;

public record OrderDto(
        UUID orderId,
        Address deliveryAddress,
        UUID restaurantId,
        BigDecimal totalPrice,
        List<OrderLine> orderLines,
        String status
) {
}