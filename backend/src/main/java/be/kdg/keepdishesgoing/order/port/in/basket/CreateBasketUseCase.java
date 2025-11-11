package be.kdg.keepdishesgoing.order.port.in.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

public interface CreateBasketUseCase {
    
    Basket createBasket(CreateBasketCommand createBasketCommand);
    
}
