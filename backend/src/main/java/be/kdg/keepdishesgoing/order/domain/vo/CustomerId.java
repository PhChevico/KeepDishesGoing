package be.kdg.keepdishesgoing.order.domain.vo;


import java.util.UUID;

public record CustomerId (UUID customerId) {
    
    public static CustomerId of(UUID uuid) {
        return new CustomerId(uuid);
    }
    
    public static CustomerId create() {
        return new CustomerId(UUID.randomUUID());
    }
    
}
