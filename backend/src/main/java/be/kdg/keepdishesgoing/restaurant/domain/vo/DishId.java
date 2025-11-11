package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.util.UUID;

public record DishId (UUID dishId) {
    
    public static DishId of(UUID uuid) {
        return new DishId(uuid);
    }
    
    public static DishId create() {
        return new DishId(UUID.randomUUID());
    }
    
}
