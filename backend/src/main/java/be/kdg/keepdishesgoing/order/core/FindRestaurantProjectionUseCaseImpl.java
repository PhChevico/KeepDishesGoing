package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.restaurant.FindRestaurantProjectionUseCase;
import be.kdg.keepdishesgoing.order.port.out.restaurant.LoadRestaurantProjectionPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRestaurantProjectionUseCaseImpl implements FindRestaurantProjectionUseCase {

    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;

    public FindRestaurantProjectionUseCaseImpl(LoadRestaurantProjectionPort loadRestaurantProjectionPort) {
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
    }

    @Override
    public List<RestaurantProjection> findAll() {
        return loadRestaurantProjectionPort.loadAll();
    }

    @Override
    public RestaurantProjection findRestaurantProjectionByName(String name) {
        return loadRestaurantProjectionPort.loadRestaurantByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + name));
    }

    @Override
    public RestaurantProjection findById(RestaurantId restaurantId) {
        return loadRestaurantProjectionPort.loadRestaurantById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with ID: " + restaurantId.restaurantId()));
    }

    @Override
    public List<RestaurantProjection> findAllByType(CuisineTypeProjection type) {
        return loadRestaurantProjectionPort.loadAllByType(type);
    }

    @Override
    public List<RestaurantProjection> findAllByPriceRange(String priceRange) {
        return loadRestaurantProjectionPort.loadAllByPriceRange(priceRange);
    }
}
