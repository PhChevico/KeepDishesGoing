package be.kdg.keepdishesgoing.order.port.in.dish;

import java.util.UUID;

public record MarkDishInStockCommand(
        UUID dishId
) {
}
