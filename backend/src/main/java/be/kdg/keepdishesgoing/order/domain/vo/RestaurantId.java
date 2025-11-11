package be.kdg.keepdishesgoing.order.domain.vo;

import java.util.UUID;

public record RestaurantId(UUID restaurantId) {
    
    public static RestaurantId of(UUID uuid) {
        return new RestaurantId(uuid);
    }
    
    public static RestaurantId create() {
        return new RestaurantId(UUID.randomUUID());
    }
    
}
