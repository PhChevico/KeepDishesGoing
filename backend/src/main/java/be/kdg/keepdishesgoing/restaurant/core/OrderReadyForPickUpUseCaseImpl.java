package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderReadyForPickupCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderReadyForPickupUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderReadyForPickUpUseCaseImpl implements OrderReadyForPickupUseCase {


    private final LoadOrderPort loadOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final OrderEventPublisherPort orderEventPublisherPort;


    public OrderReadyForPickUpUseCaseImpl(LoadOrderPort loadOrderPort, UpdateOrderPort updateOrderPort, OrderEventPublisherPort orderEventPublisherPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPort = updateOrderPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    @Override
    public void orderReadyForPickup(OrderReadyForPickupCommand orderReadyForPickupCommand) {
        RestaurantOrder restaurantOrder = loadOrderPort.loadOrderById(orderReadyForPickupCommand.orderId())
                .orElseThrow();
        
        restaurantOrder.readyForPickUp();
        updateOrderPort.updateOrder(restaurantOrder);
        orderEventPublisherPort.publishDomainEvent(restaurantOrder);
    }
}
