package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RestaurantOrderJpaRepository extends JpaRepository<RestaurantOrderJpaEntity, UUID> {
    @Query("SELECT r FROM RestaurantOrderJpaEntity r JOIN FETCH r.orderLines WHERE r.restaurantId = :id")
    List<RestaurantOrderJpaEntity> findByRestaurantIdWithOrderLines(@Param("id") UUID id);    

    @Query("SELECT r FROM RestaurantOrderJpaEntity r JOIN FETCH r.orderLines WHERE r.status = 'RECEIVED' AND r.createdAtEpochMillis < :cutoffMillis")
    List<RestaurantOrderJpaEntity> findPendingOrdersOlderThan(@Param("cutoffMillis") long cutoffMillis);

}
