package be.kdg.keepdishesgoing.order.adapter.in.response.basket;


import java.math.BigDecimal;
import java.util.UUID;

public record BasketItemDto (
        UUID dishId,
        String name,
        BigDecimal priceAtAddition,
        int quantity
){
}
