package be.kdg.keepdishesgoing.common.events.order;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRefuseEvent(
        UUID eventId,
        UUID orderId,
        String message,
        LocalDateTime occurredAt
        ) implements DomainEvent {
    
    public OrderRefuseEvent(UUID orderId,
                            String message) {
        this(UUID.randomUUID(), orderId, message, LocalDateTime.now());
    }


    @Override
    public LocalDateTime eventPit() {
        return null;
    }
}
