package be.kdg.keepdishesgoing.order.domain.vo;

import java.util.UUID;

public record BasketId (
        UUID basketId
){
    
    public static BasketId of(UUID basketId){
        return new BasketId(basketId);
    }
    
    public static BasketId create(){
        return new BasketId(UUID.randomUUID());
    }
    
    
    
}
