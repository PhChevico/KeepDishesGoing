package be.kdg.keepdishesgoing.order.adapter.in.listener;

import be.kdg.keepdishesgoing.common.events.restaurant.RestaurantPriceRangeUpdatedEvent;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantPriceRangeUpdatedPort;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantPriceRangeUpdatedProjectionCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPriceRangeUpdatedListener {

    private final Logger log = LoggerFactory.getLogger(RestaurantPriceRangeUpdatedListener.class);
    private final RestaurantPriceRangeUpdatedPort restaurantPriceRangeUpdatedPort;

    public RestaurantPriceRangeUpdatedListener(RestaurantPriceRangeUpdatedPort restaurantPriceRangeUpdatedPort) {
        this.restaurantPriceRangeUpdatedPort = restaurantPriceRangeUpdatedPort;
    }
    
    @ApplicationModuleListener
    public void restaurantPriceRangeUpdated(RestaurantPriceRangeUpdatedEvent restaurantPriceRangeUpdatedEvent) {
        log.info("Restaurant price range updated: {}", restaurantPriceRangeUpdatedEvent);
        restaurantPriceRangeUpdatedPort.project(
                new RestaurantPriceRangeUpdatedProjectionCommand(
                        restaurantPriceRangeUpdatedEvent.restaurantId(),
                        restaurantPriceRangeUpdatedEvent.newPriceRange()
                        ));
    }
}
