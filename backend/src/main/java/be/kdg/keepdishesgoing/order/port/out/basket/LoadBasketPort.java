package be.kdg.keepdishesgoing.order.port.out.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

import java.util.UUID;

public interface LoadBasketPort {
    
    Basket findBasketById(UUID basketId);
    Basket findBasketByOrderId(UUID basketId);
//    Basket findBasketByRestaurantId(UUID restaurantId);
    Basket findBasketByRestaurantIdAndAnonymousId(UUID restaurantId, UUID anonymousId);
}
