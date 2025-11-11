package be.kdg.keepdishesgoing.common.events.dish;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record MarkDishInStockEvent(
        LocalDateTime eventPit,
        UUID dishId ) implements DomainEvent {

    public MarkDishInStockEvent(UUID dishId) {
        this(LocalDateTime.now(), dishId);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}

