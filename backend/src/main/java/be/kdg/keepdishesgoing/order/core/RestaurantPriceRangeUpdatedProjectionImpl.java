package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantPriceRangeUpdatedPort;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantPriceRangeUpdatedProjectionCommand;
import be.kdg.keepdishesgoing.order.port.out.restaurant.LoadRestaurantProjectionPort;
import org.springframework.stereotype.Service;

@Service
public class RestaurantPriceRangeUpdatedProjectionImpl implements RestaurantPriceRangeUpdatedPort {
    
        private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;
        
        public RestaurantPriceRangeUpdatedProjectionImpl(LoadRestaurantProjectionPort loadRestaurantProjectionPort) {
            this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
        }

    @Override
    public void project(RestaurantPriceRangeUpdatedProjectionCommand restaurantPriceRangeUpdatedProjectionCommand) {
        RestaurantProjection restaurantProjection = loadRestaurantProjectionPort.loadById(
                RestaurantId.of(restaurantPriceRangeUpdatedProjectionCommand.restaurantId())
        );
        
        restaurantProjection.updatePriceRange(PriceRangeProjection.valueOf(restaurantPriceRangeUpdatedProjectionCommand.newPriceRange()));
        loadRestaurantProjectionPort.save(restaurantProjection);
    }
}
