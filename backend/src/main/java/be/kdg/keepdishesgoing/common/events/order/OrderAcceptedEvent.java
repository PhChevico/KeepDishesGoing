package be.kdg.keepdishesgoing.common.events.order;

import be.kdg.keepdishesgoing.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;


@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.accepted.v1'}")
public record OrderAcceptedEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID restaurantId,
        UUID orderId,
        Address pickupAddress,
        Coordinates pickupCoordinates,
        Address dropoffAddress,
        Coordinates dropoffCoordinates
) implements DomainEvent {
    
    public OrderAcceptedEvent( UUID restaurantId,
                               UUID orderId,
                               Address pickupAddress,
                               Coordinates pickupCoordinates,
                               Address dropoffAddress,
                               Coordinates dropoffCoordinates
    ){
        this(UUID.randomUUID(), LocalDateTime.now(), restaurantId, orderId, pickupAddress, 
                pickupCoordinates, dropoffAddress, dropoffCoordinates);
    }
    

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
