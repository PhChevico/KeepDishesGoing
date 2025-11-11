package be.kdg.keepdishesgoing.restaurant.port.in.order;

public interface OrderRefuseUseCase {
    
    void refuseOrder(OrderRefuseCommand orderRefuseCommand);
    
}
