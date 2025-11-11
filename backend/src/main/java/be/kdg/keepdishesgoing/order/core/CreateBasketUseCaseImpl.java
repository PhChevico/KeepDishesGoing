package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.vo.AnonymousId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.port.in.basket.CreateBasketCommand;
import be.kdg.keepdishesgoing.order.port.in.basket.CreateBasketUseCase;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import org.springframework.stereotype.Service;


@Service
public class CreateBasketUseCaseImpl implements CreateBasketUseCase {
    
    private final UpdateBasketPort updateBasketPort;
//    private final LoadBasketPort loadBasketPort;
    
    public CreateBasketUseCaseImpl(UpdateBasketPort updateBasketPort
//            LoadBasketPort loadBasketPort
    ) {
        this.updateBasketPort = updateBasketPort;
//        this.loadBasketPort = loadBasketPort;
    }

    @Override
    public Basket createBasket(CreateBasketCommand createBasketCommand) {

        Basket basket = Basket.createNew(
                RestaurantId.of(createBasketCommand.restaurantId()),
                AnonymousId.of(createBasketCommand.anonymousId())
        );

        return updateBasketPort.saveBasket(basket);
    }
}
