package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.OrderLine;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OrderId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPickedUpEventCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPublishCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderPublishedPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderPublishedImpl implements OrderPublishedPort {
    
    private final UpdateOrderPort updateOrderPort;
    private final LoadOrderPort loadOrderPort;

    public OrderPublishedImpl(UpdateOrderPort updateOrderPort, LoadOrderPort loadOrderPort) {
        this.updateOrderPort = updateOrderPort;
        this.loadOrderPort = loadOrderPort;
    }

    @Override
    @Transactional
    public void project(OrderPublishCommand orderPublishCommand) {

        RestaurantOrder restaurantOrder = new RestaurantOrder(
                OrderId.of(orderPublishCommand.orderId()),
                orderPublishCommand.deliveryAddress(),
                RestaurantId.of(orderPublishCommand.restaurantId()),
                orderPublishCommand.totalPrice(),
                orderPublishCommand.orderLines().stream()
                        .map(line -> new OrderLine(
                                DishId.of(line.dishId()),
                                line.priceAtAddition(),
                                line.quantity()
                        ))
                        .toList()
        );
        
        updateOrderPort.updateOrder(restaurantOrder);
        
    }

    @Override
    @Transactional
    public void project(OrderPickedUpEventCommand orderPickedUpEventCommand) {
        RestaurantOrder restaurantOrder = loadOrderPort.loadOrderById(orderPickedUpEventCommand.orderId())
                .orElseThrow();
        
        restaurantOrder.completed();
        updateOrderPort.updateOrder(restaurantOrder);
    }
}
