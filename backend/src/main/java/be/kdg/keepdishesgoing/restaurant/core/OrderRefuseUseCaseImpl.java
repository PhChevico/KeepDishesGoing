package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.common.events.order.OrderRefuseEvent;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderRefuseCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderRefuseUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class OrderRefuseUseCaseImpl implements OrderRefuseUseCase {
    
    private final LoadOrderPort loadOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    public OrderRefuseUseCaseImpl(LoadOrderPort loadOrderPort, UpdateOrderPort updateOrderPort,
                                  OrderEventPublisherPort orderEventPublisherPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPort = updateOrderPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    @Override
    public void refuseOrder(OrderRefuseCommand orderRefuseCommand) {
        RestaurantOrder restaurantOrder = loadOrderPort.loadOrderById(orderRefuseCommand.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        restaurantOrder.refuse(orderRefuseCommand.message());
        updateOrderPort.updateOrder(restaurantOrder);
        
        orderEventPublisherPort.publishDomainEvent(restaurantOrder);
    }
}
