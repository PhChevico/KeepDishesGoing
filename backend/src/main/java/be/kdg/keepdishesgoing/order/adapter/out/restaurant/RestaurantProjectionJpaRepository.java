package be.kdg.keepdishesgoing.order.adapter.out.restaurant;


import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantProjectionJpaRepository extends JpaRepository<RestaurantProjectionJpaEntity, UUID> {
    Optional<RestaurantProjectionJpaEntity> findByName(String name);

    List<RestaurantProjectionJpaEntity> findByCuisineType(CuisineTypeProjection cuisineType);

    List<RestaurantProjectionJpaEntity> findRestaurantProjectionJpaEntitiesByPriceRange(PriceRangeProjection priceRange);

    Optional<RestaurantProjectionJpaEntity> findRestaurantProjectionJpaEntitiesById(UUID id);
}
