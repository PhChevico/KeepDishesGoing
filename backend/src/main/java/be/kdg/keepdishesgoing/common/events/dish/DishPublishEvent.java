package be.kdg.keepdishesgoing.common.events.dish;


import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DishPublishEvent(LocalDateTime eventPit,
                               UUID dishId,
                               String name,
                               String typeOfDish,
                               List<String> foodTags,
                               String description,
                               BigDecimal price,
                               String picture,
                               String dishStatus,
                               UUID foodMenuId,
                               UUID restaurantId ) implements DomainEvent {

    

    public DishPublishEvent(UUID dishId, String name, String typeOfDish, List<String> foodTags, String description, BigDecimal price, String picture, String dishStatus, UUID foodMenuId, UUID restaurantId) {
        this(LocalDateTime.now(), dishId, name, typeOfDish, foodTags, description, price,
                picture, dishStatus, foodMenuId, restaurantId);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
