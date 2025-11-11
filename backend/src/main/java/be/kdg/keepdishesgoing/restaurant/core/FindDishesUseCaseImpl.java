package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.FindDishesUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FindDishesUseCaseImpl implements FindDishesUseCase {

    private final LoadDishesPort loadDishesPort;
    
    public FindDishesUseCaseImpl(LoadDishesPort loadDishesPort) {
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    public List<Dish> findDishesByRestaurantId(UUID restaurantId) {
        log.info("Finding dishes by restaurant id {}", loadDishesPort.loadAllDishesByRestaurantId(restaurantId));
        return loadDishesPort.loadAllDishesByRestaurantId(restaurantId);
    }
}
