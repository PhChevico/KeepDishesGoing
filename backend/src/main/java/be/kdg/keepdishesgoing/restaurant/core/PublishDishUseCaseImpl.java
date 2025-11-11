package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.PublishDishToMenuCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.PublishDishToMenuUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.DishEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.LoadFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.UpdateFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.RestaurantEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.UpdateRestaurantPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PublishDishUseCaseImpl implements PublishDishToMenuUseCase {

    private static final Logger log = LoggerFactory.getLogger(PublishDishUseCaseImpl.class);
    private final LoadDishesPort loadDishesPort;
    private final UpdateDishPort updateDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final UpdateRestaurantPort updateRestaurantPort;
    private final RestaurantEventPublisherPort restaurantEventPublisherPort;
    private final DishEventPublisherPort dishEventPublisherPort;

    public PublishDishUseCaseImpl(LoadDishesPort loadDishesPort, UpdateDishPort updateDishPort,
                                  LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort,
                                  LoadRestaurantPort loadRestaurantPort, UpdateRestaurantPort updateRestaurantPort,
                                  RestaurantEventPublisherPort restaurantEventPublisherPort,
                                  DishEventPublisherPort dishEventPublisherPort) {
        this.loadDishesPort = loadDishesPort;
        this.updateDishPort = updateDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.loadRestaurantPort = loadRestaurantPort;
        this.updateRestaurantPort = updateRestaurantPort;
        this.restaurantEventPublisherPort = restaurantEventPublisherPort;
        this.dishEventPublisherPort = dishEventPublisherPort;
    }

    @Override
    public Dish publishDishToFoodMenu(PublishDishToMenuCommand command) {
        Dish dish = loadDishesPort.loadDishById(command.dishId());

        FoodMenu menu = loadFoodMenuPort.loadMenuByWithDishes(FoodMenuId.of(command.menuId()))
                .orElseThrow(() -> new IllegalStateException("FoodMenu not found: " + command.menuId()));

        log.info("Publishing dish to menu: {}", menu.getDishes().stream().toList());
        
        dish.publishToMenu(menu);

        updateDishPort.saveDish(dish);
        updateFoodMenuPort.updateFoodMenu(menu);
        dishEventPublisherPort.publishDishEvents(dish);

        Restaurant restaurant = loadRestaurantPort.loadRestaurantById(menu.getRestaurantId().restaurantId())
                .orElseThrow();
        restaurant.updatePriceRange(menu.getAveragePrice());
        updateRestaurantPort.updateRestaurant(restaurant);
        restaurantEventPublisherPort.publishDomainEvents(restaurant);

        log.info("Published dish {} to food menu {}", dish.getName(), menu.getFoodMenuId());
        return dish;
    }

}
