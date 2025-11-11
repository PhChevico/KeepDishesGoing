package be.kdg.keepdishesgoing.order.port.in.restaurant;

import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.List;

public interface FindRestaurantProjectionUseCase {
    
    RestaurantProjection findById(RestaurantId restaurantId);
    List<RestaurantProjection> findAll();
    RestaurantProjection findRestaurantProjectionByName(String name);
    List<RestaurantProjection> findAllByType(CuisineTypeProjection type);
    List<RestaurantProjection> findAllByPriceRange(String priceRange);
    
}
