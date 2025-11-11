package be.kdg.keepdishesgoing.restaurant.port.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OwnerId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantPort {

    List<Restaurant> loadAll();
    Optional<Restaurant> loadRestaurantByName(String name);
    List<Restaurant> loadAllByType(CuisineType type);
    List<Restaurant> loadAllByPriceRange(String priceRange);
    Optional<Restaurant> loadRestaurantById(UUID restaurantId);
    Optional<Restaurant> loadRestaurantByOwnerId(OwnerId ownerId);

}
