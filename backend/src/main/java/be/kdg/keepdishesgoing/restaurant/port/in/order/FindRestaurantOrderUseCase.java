package be.kdg.keepdishesgoing.restaurant.port.in.order;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;

import java.util.List;
import java.util.UUID;

public interface FindRestaurantOrderUseCase {
    List<RestaurantOrder> findRestaurantOrders(UUID id);
}
