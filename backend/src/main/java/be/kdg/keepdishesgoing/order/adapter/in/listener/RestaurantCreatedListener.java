package be.kdg.keepdishesgoing.order.adapter.in.listener;


import be.kdg.keepdishesgoing.common.events.restaurant.RestaurantCreatedEvent;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.order.domain.vo.Picture;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.domain.vo.StreetAddress;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantCreatedPort;
import be.kdg.keepdishesgoing.order.port.in.restaurant.RestaurantCreatedProjectionCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantCreatedListener {

    private final Logger log = LoggerFactory.getLogger(RestaurantCreatedListener.class);
    private final RestaurantCreatedPort restaurantCreatedPort;

    public RestaurantCreatedListener(RestaurantCreatedPort restaurantCreatedPort) {
        this.restaurantCreatedPort = restaurantCreatedPort;
    }
    
    @ApplicationModuleListener
    public void restaurantCreated(RestaurantCreatedEvent restaurantCreatedEvent) {
        log.info("Restaurant created: {}", restaurantCreatedEvent);
        restaurantCreatedPort.project(new RestaurantCreatedProjectionCommand(
                RestaurantId.of(restaurantCreatedEvent.restaurantUUID()),
                restaurantCreatedEvent.name(),
                EmailAddress.of(restaurantCreatedEvent.email()),
                
                StreetAddress.of(
                        restaurantCreatedEvent.street(),
                        restaurantCreatedEvent.number(),
                        restaurantCreatedEvent.postalCode(),
                        restaurantCreatedEvent.city(),
                        restaurantCreatedEvent.country()),
                CuisineTypeProjection.valueOf(restaurantCreatedEvent.cuisineType()),
                restaurantCreatedEvent.defaultTimeOfPreparation(),
                Picture.of(restaurantCreatedEvent.picture()),
                PriceRangeProjection.valueOf(restaurantCreatedEvent.priceRange())
        ));

    }
    
    
}
