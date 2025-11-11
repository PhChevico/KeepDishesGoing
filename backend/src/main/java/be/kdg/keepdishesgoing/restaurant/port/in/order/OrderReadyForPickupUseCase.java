package be.kdg.keepdishesgoing.restaurant.port.in.order;

public interface OrderReadyForPickupUseCase {
    
    void orderReadyForPickup(OrderReadyForPickupCommand orderReadyForPickupCommand);
    
}
