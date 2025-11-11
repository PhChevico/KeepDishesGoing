package be.kdg.keepdishesgoing.common.events.restaurant;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestaurantPriceRangeUpdatedEvent (
        LocalDateTime eventPit,
        UUID restaurantId,
        String newPriceRange) implements DomainEvent {


    public RestaurantPriceRangeUpdatedEvent(UUID restaurantId, String newPriceRange) {
        this(LocalDateTime.now(), restaurantId, newPriceRange);
    }
    

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }



}
