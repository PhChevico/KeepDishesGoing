package be.kdg.keepdishesgoing.restaurant.adapter.out.dish;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {
    List<DishJpaEntity> findByFoodMenuId(UUID foodMenuId);

    List<DishJpaEntity> findDishJpaEntitiesByDishStatus(DishStatus dishStatus);

    @Query("SELECT d FROM DishJpaEntity d JOIN FETCH d.foodTags WHERE d.restaurantId = :restaurantId")
    List<DishJpaEntity> findAllByRestaurantIdAndFetchTags(@Param("restaurantId") UUID restaurantId);

}
