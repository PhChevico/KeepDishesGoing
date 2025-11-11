package be.kdg.keepdishesgoing.common.events.order;

import org.springframework.modulith.events.Externalized;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'delivery.' + #this.restaurantId() + '.order.location.v1'}")
public record DeliveryJobLocationUpdatedEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId,
        double lat,
        double lng
) implements DomainEvent {

    public DeliveryJobLocationUpdatedEvent(UUID restaurantId,
                                           UUID orderId,
                                           double lat,
                                           double lng) {
        this(UUID.randomUUID(), LocalDateTime.now(), restaurantId, orderId, lat, lng);
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
