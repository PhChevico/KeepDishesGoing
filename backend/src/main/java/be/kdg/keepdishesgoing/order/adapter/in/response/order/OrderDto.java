package be.kdg.keepdishesgoing.order.adapter.in.response.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(
        String orderId,
        String customerName,
        String emailAddress,
        String deliveryAddress,
        String restaurantId,
        List<OrderItemDto> items,
        BigDecimal totalPrice
) {}