package be.kdg.keepdishesgoing.restaurant.port.in.order;

public interface OrderPublishedPort {
    void project(OrderPublishCommand orderPublishCommand);
    void project(OrderPickedUpEventCommand orderPickedUpEventCommand);
    
}
