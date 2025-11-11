package be.kdg.keepdishesgoing.order.port.in.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

import java.util.UUID;

public interface FindBasketPort {
    
    Basket findBasketById(UUID id);
    
}
