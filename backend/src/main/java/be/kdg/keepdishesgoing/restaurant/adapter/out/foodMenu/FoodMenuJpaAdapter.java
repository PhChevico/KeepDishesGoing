package be.kdg.keepdishesgoing.restaurant.adapter.out.foodMenu;

import be.kdg.keepdishesgoing.restaurant.adapter.out.dish.DishJpaMapper;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.LoadFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.PersistFoodMenuPort;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FoodMenuJpaAdapter implements LoadFoodMenuPort, UpdateFoodMenuPort, PersistFoodMenuPort {
  

    private final FoodMenuJpaRepository foodMenuJpaRepository;
    
    public FoodMenuJpaAdapter(FoodMenuJpaRepository foodMenuJpaRepository) {
        this.foodMenuJpaRepository = foodMenuJpaRepository;
    }

    @Override
    public Optional<FoodMenu> loadMenuByRestaurant(RestaurantId restaurantId) {
        return foodMenuJpaRepository.findByRestaurantId(restaurantId.restaurantId())
                .map(entity -> FoodMenu.rehydrate(
                        FoodMenuId.of(entity.getId()),
                        RestaurantId.of(entity.getRestaurantId()),
                        entity.getAveragePrice()
                ));
    }

    @Override
    public Optional<FoodMenu> loadMenuById(FoodMenuId foodMenuId) {
        return foodMenuJpaRepository.findById(foodMenuId.foodMenuId())
                .map(entity -> FoodMenu.rehydrate(
                        FoodMenuId.of(entity.getId()),
                        RestaurantId.of(entity.getRestaurantId()),
                        entity.getAveragePrice()
                ));
    }

    @Override
    public Optional<FoodMenu> loadMenuByWithDishes(FoodMenuId foodMenuId) {
        return foodMenuJpaRepository.findByIdWithDishes(foodMenuId.foodMenuId())
                .map(entity -> {
                    FoodMenu menu = FoodMenu.rehydrate(
                            FoodMenuId.of(entity.getId()),
                            RestaurantId.of(entity.getRestaurantId()),
                            entity.getAveragePrice()
                    );

                    List<Dish> dishes = entity.getDishes().stream()
                            .map(DishJpaMapper::toDomain)
                            .toList();
                    menu.setDishes(dishes);

                    return menu;
                });
    }
    
    @Override
    public FoodMenu updateFoodMenu(FoodMenu foodMenu) {
        FoodMenuJpaEntity foodMenuJpaEntity = new FoodMenuJpaEntity(
                foodMenu.getFoodMenuId().foodMenuId(),
                foodMenu.getRestaurantId().restaurantId(),
                foodMenu.getAveragePrice()
        );
        
        foodMenuJpaRepository.save(foodMenuJpaEntity);
        
        return FoodMenu.rehydrate(
                FoodMenuId.of(foodMenuJpaEntity.getId()),
                RestaurantId.of(foodMenuJpaEntity.getRestaurantId()),
                foodMenuJpaEntity.getAveragePrice()
        );
        
    }

    @Override
    public FoodMenu save(FoodMenu foodMenu) {
        FoodMenuJpaEntity foodMenuJpaEntity = new FoodMenuJpaEntity(foodMenu.getFoodMenuId().foodMenuId(), 
                foodMenu.getRestaurantId().restaurantId(), foodMenu.getAveragePrice());
        
        foodMenuJpaRepository.save(foodMenuJpaEntity);
        
        return FoodMenu.rehydrate(
                FoodMenuId.of(foodMenuJpaEntity.getId()),
                RestaurantId.of(foodMenuJpaEntity.getRestaurantId()),
                foodMenuJpaEntity.getAveragePrice()
        );
    }
}
