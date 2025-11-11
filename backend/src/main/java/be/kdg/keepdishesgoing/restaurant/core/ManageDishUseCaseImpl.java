package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodTag;
import be.kdg.keepdishesgoing.restaurant.domain.TypeOfDish;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.*;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishStatusEventPublisherPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManageDishUseCaseImpl implements CreateDishUseCase, UpdateDishUseCase {

    private static final Logger log = LoggerFactory.getLogger(ManageDishUseCaseImpl.class);
    private final UpdateDishPort updateDishPort;
    private final LoadDishesPort  loadDishesPort;
    private final UpdateDishStatusEventPublisherPort updateDishStatusEventPublisherPort;

    public ManageDishUseCaseImpl(UpdateDishPort updateDishPort, LoadDishesPort loadDishesPort, UpdateDishStatusEventPublisherPort updateDishStatusEventPublisherPort) {
        this.updateDishPort = updateDishPort;
        this.loadDishesPort = loadDishesPort;
        this.updateDishStatusEventPublisherPort = updateDishStatusEventPublisherPort;
    }

    @Override
    public Dish createDish(CreateDishCommand createDishCommand) {
        
        List<FoodTag> foodTags = Optional.ofNullable(createDishCommand.foodTags())
                .orElse(Collections.emptyList())
                .stream()
                .map(String::toUpperCase)
                .map(FoodTag::valueOf)
                .toList();
    
        log.info(foodTags.toString());

        return updateDishPort.saveDish(
                Dish.createNew(
                        createDishCommand.name(),
                        TypeOfDish.valueOf(createDishCommand.typeOfDish()),
                        foodTags,
                        createDishCommand.description(),
                        createDishCommand.price(),
                        createDishCommand.picture(),
                        RestaurantId.of(createDishCommand.restaurantId())
                )
        );
    }

    @Override
    public Dish updateDish(UpdateDishCommand updateDishCommand) {
        Dish existingDish = loadDishesPort.loadDishById(updateDishCommand.dishId());
        if (existingDish == null) {
            throw new IllegalArgumentException("Dish not found: " + updateDishCommand.dishId());
        }

        List<FoodTag> foodTags = Optional.ofNullable(updateDishCommand.foodTags())
                .orElse(Collections.emptyList())
                .stream()
                .map(String::toUpperCase)
                .map(FoodTag::valueOf)
                .toList();

        existingDish.updateDetails(
                updateDishCommand.name(),
                updateDishCommand.typeOfDish(),
                foodTags,
                updateDishCommand.description(),
                updateDishCommand.price(),
                updateDishCommand.picture(),
                RestaurantId.of(updateDishCommand.restaurantId())
        );

        Dish updatedDish = updateDishPort.saveDish(existingDish);
        log.info("Updated dish with id {}", updatedDish.getDishId());
        return updatedDish;
    }

    @Override
    public List<Dish> acceptAllChanges() {
        List<Dish> drafts = loadDishesPort.loadAllDishesDraft();

        for (Dish dish : drafts) {
            dish.setDishPublish();
            updateDishPort.saveDish(dish); // save changes
            updateDishStatusEventPublisherPort.markDishInStockEvent(dish); // publish events from original object
        }
        
        return drafts;
    }

}
