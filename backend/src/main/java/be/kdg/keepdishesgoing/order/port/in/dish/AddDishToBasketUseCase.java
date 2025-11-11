package be.kdg.keepdishesgoing.order.port.in.dish;

import be.kdg.keepdishesgoing.order.domain.Basket;

public interface AddDishToBasketUseCase {
    
    Basket addDishToBasket(AddDishToBasketCommand  addDishToBasketCommand);
    
}
