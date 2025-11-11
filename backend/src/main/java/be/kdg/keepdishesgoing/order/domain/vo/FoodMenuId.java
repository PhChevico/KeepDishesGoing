package be.kdg.keepdishesgoing.order.domain.vo;

import java.util.UUID;

public record FoodMenuId(UUID foodMenuId) {
    
    public static FoodMenuId of(UUID uuid) {
        return new  FoodMenuId(uuid);
    }
    
}
