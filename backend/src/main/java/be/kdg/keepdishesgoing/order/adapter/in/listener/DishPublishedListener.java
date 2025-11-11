package be.kdg.keepdishesgoing.order.adapter.in.listener;

import be.kdg.keepdishesgoing.common.events.dish.DishPublishEvent;
import be.kdg.keepdishesgoing.common.events.dish.DishUnpublishEvent;
import be.kdg.keepdishesgoing.common.events.dish.MarkDishInStockEvent;
import be.kdg.keepdishesgoing.common.events.dish.MarkDishOutOfStockEvent;
import be.kdg.keepdishesgoing.order.port.in.dish.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class DishPublishedListener {

    private final Logger log = LoggerFactory.getLogger(DishPublishedListener.class);
    private final DishPublishedPort dishPublishedPort;

    public DishPublishedListener(DishPublishedPort dishPublishedPort) {
        this.dishPublishedPort = dishPublishedPort;
    }

    @ApplicationModuleListener
    public void dishPublished(DishPublishEvent dishPublishEvent) {
        log.info("Restaurant created: {}", dishPublishEvent);

        dishPublishedPort.project(new DishPublishedCommand(
                dishPublishEvent.dishId(),
                dishPublishEvent.name(),
                dishPublishEvent.typeOfDish(),
                dishPublishEvent.foodTags(),
                dishPublishEvent.description(),
                dishPublishEvent.price(),
                dishPublishEvent.picture(),
                dishPublishEvent.dishStatus(),
                dishPublishEvent.foodMenuId(),
                dishPublishEvent.restaurantId()
        ));
    }
    
    @ApplicationModuleListener
    public void dishUnpublished(DishUnpublishEvent dishUnpublishEvent){
        log.info("Dish {} remove from menu {}", dishUnpublishEvent.dishId(), dishUnpublishEvent.foodMenuId());

        dishPublishedPort.project(new DishUnpublishedCommand(
                dishUnpublishEvent.dishId(),
                dishUnpublishEvent.name(),
                dishUnpublishEvent.typeOfDish(),
                dishUnpublishEvent.foodTags(),
                dishUnpublishEvent.description(),
                dishUnpublishEvent.price(),
                dishUnpublishEvent.picture(),
                dishUnpublishEvent.dishStatus(),
                dishUnpublishEvent.foodMenuId(),
                dishUnpublishEvent.restaurantId()
        ));
    }
    
    
    @ApplicationModuleListener
    public void markDishOutOfStock(MarkDishOutOfStockEvent markDishOutOfStockEvent){
        log.info("Dish {} is being marked out of stock",  markDishOutOfStockEvent.dishId());
        
        dishPublishedPort.project(new MarkDishOutOfStockCommand(
                markDishOutOfStockEvent.dishId()
        ));
    }

    @ApplicationModuleListener
    public void markDishInStock(MarkDishInStockEvent markDishInStockEvent){
        log.info("Dish {} is being marked in stock",  markDishInStockEvent.dishId());

        dishPublishedPort.project(new MarkDishInStockCommand(
                markDishInStockEvent.dishId()
        ));
    }

}
