package be.kdg.keepdishesgoing.restaurant.port.out.order;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadOrderPort {
    
    Optional<RestaurantOrder> loadOrderById(UUID orderId);
    List<RestaurantOrder> loadOrdersByRestaurantId(UUID restaurantId);

    // Load orders that are still pending (RECEIVED) and created before the given cutoff instant
    List<RestaurantOrder> loadPendingOrdersOlderThan(Instant cutoff);
}
