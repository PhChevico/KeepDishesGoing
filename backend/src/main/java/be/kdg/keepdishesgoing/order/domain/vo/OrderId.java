package be.kdg.keepdishesgoing.order.domain.vo;

import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;

import java.util.UUID;

public record OrderId(UUID orderId) {

    public static OrderId of(UUID uuid) {
        return new OrderId(uuid);
    }

    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }

}
