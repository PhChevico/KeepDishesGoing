package be.kdg.keepdishesgoing.order.port.in.restaurant;


import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.order.domain.vo.Picture;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.domain.vo.StreetAddress;

public record RestaurantCreatedProjectionCommand (
        RestaurantId restaurantUUID, String name, EmailAddress email,
        StreetAddress streetAddress, CuisineTypeProjection cuisineType, int defaultTimeOfPreparation, Picture picture,
        PriceRangeProjection priceRange
){
}
