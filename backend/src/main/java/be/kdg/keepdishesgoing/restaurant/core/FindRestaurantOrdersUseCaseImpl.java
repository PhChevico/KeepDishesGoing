package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.port.in.order.FindRestaurantOrderUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindRestaurantOrdersUseCaseImpl implements FindRestaurantOrderUseCase {
    
    private final LoadOrderPort loadOrderPort;

    public FindRestaurantOrdersUseCaseImpl(LoadOrderPort loadOrderPort) {
        this.loadOrderPort = loadOrderPort;
    }


    @Override
    public List<RestaurantOrder> findRestaurantOrders(UUID id) {
        return loadOrderPort.loadOrdersByRestaurantId(id);
    }
}
