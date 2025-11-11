package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.RestaurantEventPublisherPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements RestaurantEventPublisherPort {

    private final ApplicationEventPublisher applicationEventPublisher;
    
    public RestaurantEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishDomainEvents(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        restaurant.clearDomainEvents(); 
    }

    //    @Override
//    public Restaurant save(Restaurant restaurant) {
//        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
//        return restaurant;
//    }
//
//    @Override
//    public Restaurant updateRestaurant(Restaurant restaurant) {
//        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
//        return restaurant;
//    }
}
