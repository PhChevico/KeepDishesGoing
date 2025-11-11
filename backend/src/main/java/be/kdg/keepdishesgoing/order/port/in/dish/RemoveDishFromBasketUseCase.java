package be.kdg.keepdishesgoing.order.port.in.dish;

import be.kdg.keepdishesgoing.order.domain.Basket;

public interface RemoveDishFromBasketUseCase {
    
    Basket removeDishFromBasket(RemoveDishFromBasketCommand removeDishFromBasketCommand);
    
}
