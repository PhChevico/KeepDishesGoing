package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.in.dish.*;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import org.springframework.stereotype.Service;

@Service
public class ManageBasketUseCaseImpl implements AddDishToBasketUseCase, RemoveDishFromBasketUseCase {
    
    
    private UpdateBasketPort updateBasketPort;
    private FindDishesProjectionUseCase findDishesProjectionUseCase;
    private LoadBasketPort loadBasketPort;

    public ManageBasketUseCaseImpl(UpdateBasketPort updateBasketPort,
                                   FindDishesProjectionUseCase findDishesProjectionUseCase,
                                   LoadBasketPort loadBasketPort
    ) {
        this.updateBasketPort = updateBasketPort;
        this.findDishesProjectionUseCase = findDishesProjectionUseCase;
        this.loadBasketPort = loadBasketPort;
    }

    @Override
    public Basket addDishToBasket(AddDishToBasketCommand  addDishToBasketCommand) {
        var dish = findDishesProjectionUseCase.findDishById(DishId.of(addDishToBasketCommand.dishId()));
        var basket = loadBasketPort.findBasketById(addDishToBasketCommand.basketId());
        
        basket.addDish(dish, addDishToBasketCommand.quantity());
        
        return  updateBasketPort.saveBasket(basket);
        
    }

    @Override
    public Basket removeDishFromBasket(RemoveDishFromBasketCommand removeDishFromBasketCommand) {
        var dish = findDishesProjectionUseCase.findDishById(DishId.of(removeDishFromBasketCommand.dishId()));
        var basket = loadBasketPort.findBasketById(removeDishFromBasketCommand.basketId());

        basket.removeDish(dish.getId(), removeDishFromBasketCommand.quantity());

        return  updateBasketPort.saveBasket(basket);

    }

}
