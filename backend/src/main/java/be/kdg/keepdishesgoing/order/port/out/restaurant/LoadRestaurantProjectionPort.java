package be.kdg.keepdishesgoing.order.port.out.restaurant;

import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;

import java.util.List;
import java.util.Optional;

public interface LoadRestaurantProjectionPort {

    RestaurantProjection save(RestaurantProjection restaurant);
    List<RestaurantProjection> loadAll();
    RestaurantProjection loadById(RestaurantId restaurantId);

    Optional<RestaurantProjection> loadRestaurantByName(String name);
    List<RestaurantProjection> loadAllByType(CuisineTypeProjection type);
    List<RestaurantProjection> loadAllByPriceRange(String priceRange);
    Optional<RestaurantProjection> loadRestaurantById(RestaurantId restaurantId);
}
