package be.kdg.keepdishesgoing.restaurant.port.in.foodMenu;

import java.util.UUID;

public record CreateFoodMenuCommand (
        UUID restaurantId
){
}
