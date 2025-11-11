package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.in.basket.FindBasketUseCase;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindBasketUseCaseImpl implements FindBasketUseCase {
    
    private final LoadBasketPort loadBasketPort;

    public FindBasketUseCaseImpl(LoadBasketPort loadBasketPort) {
        this.loadBasketPort = loadBasketPort;
    }

    @Override
    public Basket findBasketById(UUID basketId) {
        return loadBasketPort.findBasketById(basketId);
    }
//
//    @Override
//    public Basket findBasketByRestaurantId(UUID restaurantId) {
//        return loadBasketPort.findBasketByRestaurantId(restaurantId);
//    }


    @Override
    public Basket findBasketByRestaurantIdAndAnonymousId(UUID restaurantId, UUID anonymousId) {
        return loadBasketPort.findBasketByRestaurantIdAndAnonymousId(restaurantId, anonymousId);
    }
}
