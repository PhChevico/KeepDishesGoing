package be.kdg.keepdishesgoing.order.port.in.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

import java.util.UUID;

public interface FindBasketUseCase {
    
    Basket findBasketById(UUID basketId);
//    Basket findBasketByRestaurantId(UUID restaurantId);
    Basket findBasketByRestaurantIdAndAnonymousId(UUID restaurantId, UUID anonymousId);


}
