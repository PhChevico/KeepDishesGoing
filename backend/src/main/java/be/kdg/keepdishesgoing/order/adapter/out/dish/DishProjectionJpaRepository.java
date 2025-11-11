package be.kdg.keepdishesgoing.order.adapter.out.dish;

import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DishProjectionJpaRepository extends JpaRepository<DishProjectionJpaEntity, UUID>{

    void removeById(UUID id);

    List<DishProjectionJpaEntity> findAllByTypeOfDish(TypeOfDishProjection typeOfDish);

    List<DishProjectionJpaEntity> findAllByRestaurantId(UUID restaurantId);

    @Query("""
        SELECT d
        FROM DishProjectionJpaEntity d
        JOIN d.foodTags t
        WHERE t = :foodTag
    """)
    List<DishProjectionJpaEntity> findAllByFoodTag(@Param("foodTag") String foodTag);
}
