package be.kdg.keepdishesgoing.order.port.out.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.vo.BasketId;

public interface UpdateBasketPort {
    
    Basket saveBasket(Basket basket);
    void deleteBasketById(BasketId basketId);
}
