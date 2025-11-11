package be.kdg.keepdishesgoing.restaurant.port.in.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OwnerId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.List;

public interface FindRestaurantPort {
    
    List<Restaurant> findAll();
    Restaurant findRestaurantByName(String name);
    List<Restaurant> findAllByType(CuisineType type);
    List<Restaurant> findAllByPriceRange(String priceRange);
    Restaurant findRestaurantById(RestaurantId restaurantId);
    Restaurant findRestaurantByOwnerId(OwnerId ownerId);
}
