package be.kdg.keepdishesgoing.order.adapter.in.response.restaurant;


import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;

import java.util.UUID;

public record RestaurantDto(
        UUID restaurantId,
        String name,
        String email,
        String street,
        int number,
        int postalCode,
        String city,
        String country,
        CuisineTypeProjection cuisineType,
        int defaultTimePreparation,
        String picture,
        PriceRangeProjection priceRange
) {}
