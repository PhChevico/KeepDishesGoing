package be.kdg.keepdishesgoing.order.adapter.in.response.order;

import java.math.BigDecimal;

public record OrderItemDto(
        String dishId,
        BigDecimal price,
        int quantity
) {}