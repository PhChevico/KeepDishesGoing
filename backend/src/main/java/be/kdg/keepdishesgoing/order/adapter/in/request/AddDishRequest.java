package be.kdg.keepdishesgoing.order.adapter.in.request;

import java.util.UUID;

public record AddDishRequest (
        UUID dishId,
        String dishName,
        int quantity
        
){
}
