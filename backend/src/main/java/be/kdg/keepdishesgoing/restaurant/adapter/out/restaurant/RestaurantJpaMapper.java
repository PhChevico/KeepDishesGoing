package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurant;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.*;

public class RestaurantJpaMapper {

    private RestaurantJpaMapper() {}

    public static RestaurantJpaEntity fromDomain(Restaurant restaurant) {
        RestaurantJpaEntity e = new RestaurantJpaEntity();
        e.setId(restaurant.getRestaurantId() == null ? null : restaurant.getRestaurantId().restaurantId());
        e.setOwnerId(restaurant.getOwnerId().ownerId());
        e.setName(restaurant.getName());
        e.setEmail(restaurant.getEmail().emailAddress());
        e.setStreet(restaurant.getAddress().street());
        e.setNumber(restaurant.getAddress().number());
        e.setPostalCode(restaurant.getAddress().postalCode());
        e.setCity(restaurant.getAddress().city());
        e.setCountry(restaurant.getAddress().country());
        e.setCuisineType(restaurant.getCuisineType());
        e.setDefaultTimePreparation(restaurant.getDefaultTimePreparation());
        e.setPicture(restaurant.getPicture().toString());
        e.setPriceRange(restaurant.getPriceRange());
        return e;
    }

    public static Restaurant toDomain(RestaurantJpaEntity e) {
        return Restaurant.rehydrate(
                RestaurantId.of(e.getId()),
                OwnerId.of(e.getOwnerId()),
                e.getName(),
                EmailAddress.of(e.getEmail()),
                Address.of(e.getStreet(), e.getNumber(), e.getPostalCode(), e.getCity(), e.getCountry()),
                e.getCuisineType(),
                e.getDefaultTimePreparation(),
                Picture.of(e.getPicture()),
                e.getPriceRange()
        );
    }
}
