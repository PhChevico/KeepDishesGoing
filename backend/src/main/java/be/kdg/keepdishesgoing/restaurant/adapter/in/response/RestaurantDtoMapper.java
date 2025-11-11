package be.kdg.keepdishesgoing.restaurant.adapter.in.response;

import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;

public final class RestaurantDtoMapper {

    private RestaurantDtoMapper() {
    }

    public static RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getRestaurantId().restaurantId(),
                restaurant.getName(),
                restaurant.getEmail().emailAddress(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getCuisineType(),
                restaurant.getDefaultTimePreparation(),
                restaurant.getPicture().url(),
                restaurant.getPriceRange()
        );
    }
    
}
