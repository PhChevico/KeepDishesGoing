package be.kdg.keepdishesgoing.restaurant.adapter.in.response;

import java.util.UUID;

public record FoodMenuDto (
        UUID id,
        UUID restaurantId
){
}
