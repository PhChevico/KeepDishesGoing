package be.kdg.keepdishesgoing.order.adapter.in.request;

import java.util.UUID;

public record RemoveDishRequest(
        UUID dishId,
        int quantity
) {
}
