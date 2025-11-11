package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.DishStatusProjection;
import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.dish.*;
import be.kdg.keepdishesgoing.order.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.order.port.out.dish.PersistDishPort;
import be.kdg.keepdishesgoing.order.port.out.dish.RemoveDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishPublishedImpl implements DishPublishedPort {
    
    private final PersistDishPort persistDishPort;
    private final LoadDishesPort loadDishesPort;
    private final RemoveDishPort removeDishPort;


    public DishPublishedImpl(PersistDishPort persistDishPort, LoadDishesPort loadDishesPort, RemoveDishPort removeDishPort) {
        this.persistDishPort = persistDishPort;
        this.loadDishesPort = loadDishesPort;
        this.removeDishPort = removeDishPort;
    }


    @Override
    @Transactional
    public void project(DishPublishedCommand dishPublishedCommand) {
        List<FoodTagProjection> foodTags = dishPublishedCommand.foodTags().stream()
                .map(FoodTagProjection::valueOf)  
                .toList();

        DishProjection dishProjection = new DishProjection(
                DishId.of(dishPublishedCommand.dishId()),
                dishPublishedCommand.name(),
                TypeOfDishProjection.valueOf(dishPublishedCommand.typeOfDish()),
                foodTags,
                dishPublishedCommand.description(),
                dishPublishedCommand.price(),
                dishPublishedCommand.picture(),
                DishStatusProjection.valueOf(dishPublishedCommand.dishStatus()),
                FoodMenuId.of(dishPublishedCommand.foodMenuId()),
                RestaurantId.of(dishPublishedCommand.restaurantId())
        );
        
        persistDishPort.save(dishProjection);
    }

    @Override
    @Transactional    
    public void project(DishUnpublishedCommand dishUnpublishedCommand) {
        DishProjection dishProjection = loadDishesPort.loadDishById(dishUnpublishedCommand.dishId());
        removeDishPort.removeDishById(dishProjection.getId().dishId());
    }

    @Override
    @Transactional
    public void project(MarkDishOutOfStockCommand markDishOutOfStockCommand) {
        DishProjection dishProjection = loadDishesPort.loadDishById(markDishOutOfStockCommand.dishId());
        dishProjection.setDishOutOfStock();
        persistDishPort.save(dishProjection);
    }

    @Override
    @Transactional
    public void project(MarkDishInStockCommand markDishInStockCommand) {
        DishProjection dishProjection = loadDishesPort.loadDishById(markDishInStockCommand.dishId());
        dishProjection.setDishInStock();
        persistDishPort.save(dishProjection);
    }
}
