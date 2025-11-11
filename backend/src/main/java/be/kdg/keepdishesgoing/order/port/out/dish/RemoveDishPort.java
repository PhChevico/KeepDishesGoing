package be.kdg.keepdishesgoing.order.port.out.dish;


import java.util.UUID;


public interface RemoveDishPort {

    void removeDishById(UUID dishId);
    
}
