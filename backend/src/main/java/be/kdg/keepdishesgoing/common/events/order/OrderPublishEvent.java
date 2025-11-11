package be.kdg.keepdishesgoing.common.events.order;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderPublishEvent(
        LocalDateTime eventPit,
        UUID orderId,
        
        String street,
        int number,
        int postalCode,
        String city,
        String country,
        
        UUID restaurantId,
        List<OrderLine> orderLines,
        BigDecimal totalPrice
) implements DomainEvent {
    
    public OrderPublishEvent(UUID orderId,         
                             String street,
                             int number,
                             int postalCode,
                             String city,
                             String country, 
                             UUID restaurantId,
                             List<OrderLine> orderLines, BigDecimal totalPrice){
        this(LocalDateTime.now(), orderId, street, number, postalCode, city, country, restaurantId, orderLines, totalPrice);
    }


    @Override
    public LocalDateTime eventPit() {
        return null;
    }

    public record OrderLine(
            UUID dishId,
            BigDecimal priceAtAddition,
            int quantity
    ) {}

}