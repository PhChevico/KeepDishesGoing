package be.kdg.keepdishesgoing.order.adapter.out.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketJpaRepository extends JpaRepository<BasketJpaEntity, UUID> {
    Optional<BasketJpaEntity> findByRestaurantId(UUID restaurantId);

    Optional<BasketJpaEntity> findByOrderId(UUID orderId);
    
    Optional<BasketJpaEntity> findByRestaurantIdAndAnonymousId(UUID restaurantId, UUID anonymousId);
}
