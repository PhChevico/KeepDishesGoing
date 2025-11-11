package be.kdg.keepdishesgoing.restaurant.port.in.order;

public interface OrderAcceptUseCase {
    
    void acceptOrder(OrderAcceptCommand orderAcceptCommand);
    
}
