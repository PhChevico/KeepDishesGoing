package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderEventPublisherPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderEventPublisher implements OrderEventPublisherPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RestaurantOrderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishDomainEvent(RestaurantOrder restaurantOrder) {
        restaurantOrder.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        restaurantOrder.clearDomainEvents();
    }
}
