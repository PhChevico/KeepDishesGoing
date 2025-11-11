package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.UnpublishDishCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.UnpublishDishUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.DishEventUnpublishPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.LoadFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.UpdateFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.UpdateRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.RestaurantEventPublisherPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UnpublishDishUseCaseImpl implements UnpublishDishUseCase {

    private static final Logger log = LoggerFactory.getLogger(UnpublishDishUseCaseImpl.class);

    private final LoadDishesPort loadDishesPort;
    private final UpdateDishPort updateDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final UpdateRestaurantPort updateRestaurantPort;
    private final RestaurantEventPublisherPort restaurantEventPublisherPort;
    private final DishEventUnpublishPort dishEventUnpublishPort;

    public UnpublishDishUseCaseImpl(
            LoadDishesPort loadDishesPort,
            UpdateDishPort updateDishPort,
            LoadFoodMenuPort loadFoodMenuPort,
            UpdateFoodMenuPort updateFoodMenuPort,
            LoadRestaurantPort loadRestaurantPort,
            UpdateRestaurantPort updateRestaurantPort,
            RestaurantEventPublisherPort restaurantEventPublisherPort,
            DishEventUnpublishPort dishEventUnpublishPort
    ) {
        this.loadDishesPort = loadDishesPort;
        this.updateDishPort = updateDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.loadRestaurantPort = loadRestaurantPort;
        this.updateRestaurantPort = updateRestaurantPort;
        this.restaurantEventPublisherPort = restaurantEventPublisherPort;
        this.dishEventUnpublishPort = dishEventUnpublishPort;
    }

    @Override
    public Dish unpublishDish(UnpublishDishCommand command) {
        Dish dish = loadDishesPort.loadDishById(command.dishId());

        FoodMenu menu = loadFoodMenuPort
                .loadMenuById(FoodMenuId.of(dish.getFoodMenuId().foodMenuId()))
                .orElseThrow(() -> new IllegalStateException(
                        "FoodMenu not found: " + dish.getFoodMenuId().foodMenuId()
                ));

        // Delegate domain logic to the aggregate
        dish.unpublishFromMenu(menu);

        updateDishPort.saveDish(dish);
        updateFoodMenuPort.updateFoodMenu(menu);
        dishEventUnpublishPort.unpublishDishEvents(dish);
        
        // Update restaurant info
        Restaurant restaurant = loadRestaurantPort.loadRestaurantById(menu.getRestaurantId().restaurantId())
                .orElseThrow();
        restaurant.updatePriceRange(menu.getAveragePrice());
        updateRestaurantPort.updateRestaurant(restaurant);
        restaurantEventPublisherPort.publishDomainEvents(restaurant);

        log.info("Unpublished dish {} from food menu {}", dish.getName(), menu.getFoodMenuId());
        return dish;
    }
}
