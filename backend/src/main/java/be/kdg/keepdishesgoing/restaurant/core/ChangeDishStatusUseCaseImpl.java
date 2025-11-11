package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.ChangeDishStatusCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.dish.ChangeDishStatusUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.LoadDishesPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishStatusEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.dish.UpdateDishPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeDishStatusUseCaseImpl implements ChangeDishStatusUseCase {

    private final UpdateDishPort updateDishPort;
    private final LoadDishesPort loadDishesPort;
    private final UpdateDishStatusEventPublisherPort updateDishStatusEventPublisherPort;

    public ChangeDishStatusUseCaseImpl(UpdateDishPort updateDishPort, LoadDishesPort loadDishesPort,
                                       UpdateDishStatusEventPublisherPort updateDishStatusEventPublisherPort) {
        this.updateDishPort = updateDishPort;
        this.loadDishesPort = loadDishesPort;
        this.updateDishStatusEventPublisherPort = updateDishStatusEventPublisherPort;
    }

    @Override
    public Dish markDishOutOfStock(ChangeDishStatusCommand command) {
        Dish dish = loadDishesPort.loadDishById(command.dishId());
        dish.setDishOutOfStock();
        
        updateDishPort.saveDish(dish);
        
        updateDishStatusEventPublisherPort.markDishOutOfStockEvent(dish);
        
        return dish;
    }

    @Override
    public Dish markDishInStock(ChangeDishStatusCommand command) {
        Dish dish = loadDishesPort.loadDishById(command.dishId());
        dish.setDishInStock();

        updateDishPort.saveDish(dish);

        updateDishStatusEventPublisherPort.markDishInStockEvent(dish);

        return dish;
    }
}
