package be.kdg.keepdishesgoing.order.port.in.restaurant;

import java.util.UUID;

public record RestaurantPriceRangeUpdatedProjectionCommand (
        UUID restaurantId,
        String newPriceRange
){
}
