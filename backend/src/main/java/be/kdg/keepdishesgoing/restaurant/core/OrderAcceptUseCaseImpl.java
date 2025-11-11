package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.port.out.coordinates.GeocodingServicePort;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderAcceptCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderAcceptUseCase;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderAcceptUseCaseImpl implements OrderAcceptUseCase {
    
    
    private final LoadOrderPort loadOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final GeocodingServicePort geocodingServicePort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final OrderEventPublisherPort  orderEventPublisherPort;

    public OrderAcceptUseCaseImpl(LoadOrderPort loadOrderPort, UpdateOrderPort updateOrderPort,
                                  GeocodingServicePort geocodingServicePort,
                                  LoadRestaurantPort loadRestaurantPort,
                                  OrderEventPublisherPort orderEventPublisherPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPort = updateOrderPort;
        this.geocodingServicePort = geocodingServicePort;
        this.loadRestaurantPort = loadRestaurantPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }


    @Override
    public void acceptOrder(OrderAcceptCommand orderAcceptCommand) {
        var restaurantOrder = loadOrderPort.loadOrderById(orderAcceptCommand.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var restaurant = loadRestaurantPort.loadRestaurantById(
                        restaurantOrder.getRestaurantId().restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        var pickupCoordinates = geocodingServicePort.geocode(restaurant.getAddress());
        var dropoffCoordinates = geocodingServicePort.geocode(restaurantOrder.getDeliveryAddress());

        restaurantOrder.accept(restaurant.getAddress(), pickupCoordinates, dropoffCoordinates);

        updateOrderPort.updateOrder(restaurantOrder);
        orderEventPublisherPort.publishDomainEvent(restaurantOrder);
    }

}
