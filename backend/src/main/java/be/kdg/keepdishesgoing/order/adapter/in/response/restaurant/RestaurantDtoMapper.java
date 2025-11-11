package be.kdg.keepdishesgoing.order.adapter.in.response.restaurant;

import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;

public final class RestaurantDtoMapper {

    private RestaurantDtoMapper() {
    }

    public static RestaurantDto toDto(RestaurantProjection restaurantProjection) {
        return new RestaurantDto(
                restaurantProjection.getRestaurantId().restaurantId(),
                restaurantProjection.getName(),
                restaurantProjection.getEmail().emailAddress(),
                restaurantProjection.getAddress().street(),
                restaurantProjection.getAddress().number(),
                restaurantProjection.getAddress().postalCode(),
                restaurantProjection.getAddress().city(),
                restaurantProjection.getAddress().country(),
                restaurantProjection.getCuisineType(),
                restaurantProjection.getDefaultTimePreparation(),
                restaurantProjection.getPicture().url(),
                restaurantProjection.getPriceRange()
        );
    }
    
}
