package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.dish.FindDishesProjectionUseCase;
import be.kdg.keepdishesgoing.order.port.out.dish.LoadDishesPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FindDishProjectionUseCaseImpl implements FindDishesProjectionUseCase {

    private final LoadDishesPort loadDishesPort;

    public FindDishProjectionUseCaseImpl(LoadDishesPort loadDishesPort) {
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    public List<DishProjection> findDishesByRestaurant(RestaurantId restaurantId) {
        return loadDishesPort.loadDishesByRestaurantId(restaurantId.restaurantId());
    }

    @Override
    public DishProjection findDishById(DishId dishId) {
        return loadDishesPort.loadDishById(dishId.dishId());
    }

    @Override
    public List<DishProjection> findAll() {
        return loadDishesPort.loadAll();
    }

    @Override
    public List<DishProjection> findAllByType(String type) {
        return loadDishesPort.loadAllByType(TypeOfDishProjection.valueOf(type));
    }

    @Override
    public List<DishProjection> findAllByFoodTag(String foodTag) {
        return loadDishesPort.loadAllByFoodTag(FoodTagProjection.valueOf(foodTag));
    }

    @Override
    public List<DishProjection> findAllDishesSorted(String sort, String direction) {
        return loadDishesPort.loadAllDishesSorted(sort, direction);
    }

    @Override
    public List<DishProjection> findAllDishesSortedAndFiltered(String sort, String direction, String type, String foodTag) {
        return loadDishesPort.loadAllDishesSortedAndFiltered(sort, direction, type, foodTag);
    }

    @Override
    public List<DishProjection> findAllDishesSortedAndFilteredByRestaurant(RestaurantId restaurantId, String sort, String direction, String type, String foodTag) {
        return loadDishesPort.loadAllDishesSortedAndFilteredByRestaurant(restaurantId, sort, direction, type, foodTag);
    }
}
