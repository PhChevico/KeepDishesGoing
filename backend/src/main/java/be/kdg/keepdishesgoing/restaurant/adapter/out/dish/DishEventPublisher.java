package be.kdg.keepdishesgoing.restaurant.adapter.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.DishEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.DishEventUnpublishPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishStatusEventPublisherPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DishEventPublisher implements DishEventPublisherPort, DishEventUnpublishPort, UpdateDishStatusEventPublisherPort {

    private final ApplicationEventPublisher applicationEventPublisher;


    public DishEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishDishEvents(Dish dish) {
        dish.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        dish.clearDomainEvents();
    }

    @Override
    public void unpublishDishEvents(Dish dish) {
        dish.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        dish.clearDomainEvents();
    }

    @Override
    public void markDishOutOfStockEvent(Dish dish) {
        dish.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        dish.clearDomainEvents();
    }

    @Override
    public void markDishInStockEvent(Dish dish) {
        dish.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        dish.clearDomainEvents();
    }
}
