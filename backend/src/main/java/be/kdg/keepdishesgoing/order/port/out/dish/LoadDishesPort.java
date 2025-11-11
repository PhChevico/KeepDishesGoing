package be.kdg.keepdishesgoing.order.port.out.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.List;
import java.util.UUID;

public interface LoadDishesPort {
    
    DishProjection loadDishById(UUID id);
    List<DishProjection> loadDishesByRestaurantId(UUID restaurantId);
    List<DishProjection> loadAll();
    List<DishProjection> loadAllByType(TypeOfDishProjection type);
    List<DishProjection> loadAllByFoodTag(FoodTagProjection foodTag);
    List<DishProjection> loadAllDishesSorted(String sort, String direction);
    List<DishProjection> loadAllDishesSortedAndFiltered(String sort, String direction, String type, String foodTag);

    List<DishProjection> loadAllDishesSortedAndFilteredByRestaurant(RestaurantId restaurantId, String sort, String direction, String type, String foodTag);
}
