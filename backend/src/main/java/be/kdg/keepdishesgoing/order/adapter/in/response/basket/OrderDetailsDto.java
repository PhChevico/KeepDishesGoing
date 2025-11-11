package be.kdg.keepdishesgoing.order.adapter.in.response.basket;

import be.kdg.keepdishesgoing.order.domain.BasketStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDetailsDto(
        UUID basketId,
        UUID restaurantId,
        List<BasketItemDto> items,
        BigDecimal totalPrice,
        BasketStatus basketStatus,
        String message,
        UUID anonymousId,
        String paymentSessionId,

        ClientInfoDto clientInfo,
        AddressDto deliveryAddress
) {
}