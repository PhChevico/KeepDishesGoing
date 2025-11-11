package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OwnerId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.order.FindRestaurantOrderUseCase;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.FindRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FindRestaurantUseCaseImplDishes implements FindRestaurantPort {
    
    private final LoadRestaurantPort loadRestaurantPort;
    
    public FindRestaurantUseCaseImplDishes(LoadRestaurantPort loadRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    public List<Restaurant> findAll() {
        return loadRestaurantPort.loadAll();
    }
    
    @Override
    public Restaurant findRestaurantByName(String restaurantName) {
        return loadRestaurantPort.loadRestaurantByName(restaurantName)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Override
    public List<Restaurant> findAllByType(CuisineType type) {
        return loadRestaurantPort.loadAllByType(type);
    }

    @Override
    public List<Restaurant> findAllByPriceRange(String priceRange) {
        return loadRestaurantPort.loadAllByPriceRange(priceRange);
    }

    @Override
    public Restaurant findRestaurantById(RestaurantId restaurantId) {
        return loadRestaurantPort.loadRestaurantById(restaurantId.restaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Override
    public Restaurant findRestaurantByOwnerId(OwnerId ownerId) {
        return loadRestaurantPort.loadRestaurantByOwnerId(ownerId)
                .orElse(null);
    }

}
