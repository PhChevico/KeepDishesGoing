package be.kdg.keepdishesgoing.order.port.in.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

public interface CheckoutBasketUseCase {

    Basket checkoutBasket(CheckoutBasketCommand checkoutBasketCommand);

}
