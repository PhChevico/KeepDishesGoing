package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.PriceRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {
    Optional<RestaurantJpaEntity> findByName(String name);

    List<RestaurantJpaEntity> findByCuisineType(CuisineType cuisineType);
    
    List<RestaurantJpaEntity> findRestaurantJpaEntitiesByPriceRange(PriceRange priceRange);

    Optional<RestaurantJpaEntity> findRestaurantJpaEntitiesById(UUID id);

    Optional<RestaurantJpaEntity> findRestaurantJpaEntitiesByOwnerId(UUID ownerId);
}
