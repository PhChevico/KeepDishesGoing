package be.kdg.keepdishesgoing.restaurant.adapter.in.request;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.PriceRange;


public record CreateRestaurantRequest(
        String name,
        String email,
        String street,
        int number,
        int postalCode,
        String city,
        String country,
        CuisineType cuisineType,
        int defaultTimePreparation,
        String picture,
        PriceRange priceRange
) {
}
