package be.kdg.keepdishesgoing.order.port.in.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.Collection;
import java.util.List;

public interface FindDishesProjectionUseCase {
    
    List<DishProjection> findDishesByRestaurant(RestaurantId restaurantId);
    DishProjection findDishById(DishId dishId);
    List<DishProjection> findAllByFoodTag(String foodTag);
    List<DishProjection> findAll();
    List<DishProjection> findAllByType(String type);
    List<DishProjection> findAllDishesSorted(String sort, String direction);

    List<DishProjection> findAllDishesSortedAndFiltered(String sort, String direction, String type, String foodTag);

    List<DishProjection> findAllDishesSortedAndFilteredByRestaurant(RestaurantId restaurantId, String sort, String direction, String type, String foodTag);
}
