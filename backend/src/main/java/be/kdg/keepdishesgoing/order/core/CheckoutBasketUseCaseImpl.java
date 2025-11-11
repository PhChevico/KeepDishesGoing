package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.in.basket.CheckoutBasketCommand;
import be.kdg.keepdishesgoing.order.port.in.basket.CheckoutBasketUseCase;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutBasketUseCaseImpl implements CheckoutBasketUseCase {

    private final LoadBasketPort loadBasketPort;
    private final UpdateBasketPort updateBasketPort;

    public CheckoutBasketUseCaseImpl(LoadBasketPort loadBasketPort, UpdateBasketPort updateBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.updateBasketPort = updateBasketPort;
    }

    @Override
    public Basket checkoutBasket(CheckoutBasketCommand checkoutBasketCommand) {
        Basket basket = loadBasketPort.findBasketById(checkoutBasketCommand.basketId());

        if (basket == null) {
            throw new IllegalArgumentException("Basket not found with id: " + checkoutBasketCommand.basketId());
        }
        
        basket.checkout(
                checkoutBasketCommand.firstName(),
                checkoutBasketCommand.lastName(),
                checkoutBasketCommand.street(),
                checkoutBasketCommand.number(),
                checkoutBasketCommand.postalCode(),
                checkoutBasketCommand.city(),
                checkoutBasketCommand.country(),
                checkoutBasketCommand.emailAddress()
        );
        
        log.info("Checkout Basket IN USE CASE IMPL: " + checkoutBasketCommand.firstName() + " " + checkoutBasketCommand.lastName());
        
        updateBasketPort.saveBasket(basket);

        return basket;
        
    }
}
