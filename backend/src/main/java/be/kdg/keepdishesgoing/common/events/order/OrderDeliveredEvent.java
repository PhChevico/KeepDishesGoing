package be.kdg.keepdishesgoing.common.events.order;

import be.kdg.keepdishesgoing.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'delivery.' + #this.restaurantId() + '.order.delivered.v1'}")
public record OrderDeliveredEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId
) implements DomainEvent {


    public OrderDeliveredEvent(UUID restaurantId,
                               UUID orderId) {
        this(UUID.randomUUID(), LocalDateTime.now(), restaurantId, orderId);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
