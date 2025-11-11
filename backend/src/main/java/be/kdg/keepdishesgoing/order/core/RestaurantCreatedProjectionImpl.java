package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantCreatedPort;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantCreatedProjectionCommand;
import be.kdg.keepdishesgoing.order.port.out.restaurant.LoadRestaurantProjectionPort;
import org.springframework.stereotype.Service;

@Service
public class RestaurantCreatedProjectionImpl implements RestaurantCreatedPort {
    
    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;
    
    
    public RestaurantCreatedProjectionImpl(LoadRestaurantProjectionPort loadRestaurantProjectionPort) {
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
    }

    @Override
    public void project(RestaurantCreatedProjectionCommand restaurantCreatedProjectionCommand) {
        RestaurantProjection restaurantProjection = new RestaurantProjection(
                restaurantCreatedProjectionCommand.restaurantUUID(),
                restaurantCreatedProjectionCommand.name(),
                restaurantCreatedProjectionCommand.email(),
                restaurantCreatedProjectionCommand.streetAddress(),
                restaurantCreatedProjectionCommand.cuisineType(),
                restaurantCreatedProjectionCommand.defaultTimeOfPreparation(),
                restaurantCreatedProjectionCommand.picture(),
                restaurantCreatedProjectionCommand.priceRange()
        );
        loadRestaurantProjectionPort.save(restaurantProjection);
        
    }

}
