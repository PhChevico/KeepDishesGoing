package be.kdg.keepdishesgoing.restaurant.port.out.order;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;

public interface UpdateOrderPort {
    
    RestaurantOrder updateOrder(RestaurantOrder restaurantOrder);
    
}
