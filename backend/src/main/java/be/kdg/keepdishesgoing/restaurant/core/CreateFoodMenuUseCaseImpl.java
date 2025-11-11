package be.kdg.keepdishesgoing.restaurant.core;


import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import be.kdg.keepdishesgoing.restaurant.domain.exceptions.RestaurantOwnerNotFound;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.foodMenu.CreateFoodMenuCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.foodMenu.CreateFoodMenuUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.foodMenu.PersistFoodMenuPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateFoodMenuUseCaseImpl implements CreateFoodMenuUseCase {
    
    private final PersistFoodMenuPort persistFoodMenuPort;

    public CreateFoodMenuUseCaseImpl(PersistFoodMenuPort persistFoodMenuPort) {
        this.persistFoodMenuPort = persistFoodMenuPort;
    }

    @Override
    public FoodMenu createFoodMenu(CreateFoodMenuCommand createFoodMenuCommand) throws RestaurantOwnerNotFound {
        FoodMenu foodMenu = FoodMenu.createNew(
                RestaurantId.of(createFoodMenuCommand.restaurantId())
        );
        
        persistFoodMenuPort.save(foodMenu);
        
        return foodMenu;
    }
}
