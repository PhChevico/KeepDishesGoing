package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.util.UUID;

public record FoodMenuId(UUID foodMenuId) {

    public static FoodMenuId of(UUID uuid) {
        return new FoodMenuId(uuid);
    }

    public static FoodMenuId create() {
        return new FoodMenuId(UUID.randomUUID());
    }
    
}
