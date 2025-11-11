package be.kdg.keepdishesgoing.order.domain.vo;

import java.util.UUID;

public record AnonymousId (
        UUID anonymousId
) {
    
    public static AnonymousId of(UUID anonymousId){
        return new AnonymousId(anonymousId);
    }
    
    public static AnonymousId create(){
        return  new AnonymousId(UUID.randomUUID());
    }
    
}
