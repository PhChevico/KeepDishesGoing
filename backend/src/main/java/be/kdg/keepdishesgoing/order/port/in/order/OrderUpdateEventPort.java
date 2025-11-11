package be.kdg.keepdishesgoing.order.port.in.order;

public interface OrderUpdateEventPort {
    
    void project(OrderAcceptedEventCommand orderAcceptedEventCommand);
    void project(OrderRefusedEventCommand orderRefusedEventCommand);
    void project(OrderReadyForPickupEventCommand orderReadyForPickupEventCommand);
    void project(OrderPickedUpEventCommand orderPickedUpEventCommand);
    void project(OrderDeliveredEventCommand orderDeliveredEventCommand);
    void project(DeliveryJobLocationUpdatedCommand deliveryJobLocationUpdatedCommand);
}
