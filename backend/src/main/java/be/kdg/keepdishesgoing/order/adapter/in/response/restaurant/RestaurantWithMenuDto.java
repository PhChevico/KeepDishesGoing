package be.kdg.keepdishesgoing.order.adapter.in.response.restaurant;

import be.kdg.keepdishesgoing.order.adapter.in.response.dish.DishDto;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;

import java.util.List;
import java.util.UUID;

public record RestaurantWithMenuDto(
        UUID restaurantId,
        String name,
        List<DishDto> menu,
        String email,
        String street,
        int number,
        int postalCode,
        String city,
        String country,
        CuisineTypeProjection cuisineType,
        int defaultTimePreparation,
        String picture) {
}
