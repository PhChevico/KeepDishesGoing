package be.kdg.keepdishesgoing.restaurant.adapter.out.foodMenu;

import be.kdg.keepdishesgoing.restaurant.domain.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FoodMenuJpaRepository extends JpaRepository<FoodMenuJpaEntity, UUID> {
    Optional<FoodMenuJpaEntity> findByRestaurantId(UUID restaurantId);

    @Query("SELECT m FROM FoodMenuJpaEntity m LEFT JOIN FETCH m.dishes WHERE m.id = :menuId")
    Optional<FoodMenuJpaEntity> findByIdWithDishes(@Param("menuId") UUID menuId);
}
