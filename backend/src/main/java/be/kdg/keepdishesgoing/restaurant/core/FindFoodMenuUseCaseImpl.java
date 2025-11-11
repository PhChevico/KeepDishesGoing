package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.FindFoodMenuUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.LoadFoodMenuPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FindFoodMenuUseCaseImpl implements FindFoodMenuUseCase {

    private final LoadFoodMenuPort loadFoodMenuPort;
    private final LoadDishesPort loadDishesPort;
    
    public FindFoodMenuUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort,  LoadDishesPort loadDishesPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.loadDishesPort = loadDishesPort;
    }
    
    @Override
    public FoodMenu findFoodMenuByRestaurant(RestaurantId restaurantId) {
        return loadFoodMenuPort.loadMenuByRestaurant(restaurantId)
                .orElseThrow(() -> new RuntimeException("Food menu not found"));
    }
    
    @Override
    public List<Dish> findDishesByFoodMenu(FoodMenuId foodMenuId){
        List<Dish> foodMenu = new ArrayList<>();
        List<Dish> dishes =  loadDishesPort.loadDishesByMenu(foodMenuId.foodMenuId());
        
        for(Dish dish : dishes){
            if(dish.getDishStatus() == DishStatus.PUBLISHED || dish.getDishStatus() == DishStatus.OUT_OF_STOCK){
                foodMenu.add(dish);
            }
        }
        
        return foodMenu;
        
    }

    @Override
    public Dish findDishById(UUID dishId) {
        return loadDishesPort.loadDishById(dishId);
    }
}
