package be.kdg.keepdishesgoing.order.adapter.in.request;

import java.util.UUID;

public record PublishOrderRequest(
        UUID basketId,
        String firstName,
        String lastName,
        String street,
        int number,
        int postalCode,
        String city,
        String country,
        String emailAddress,
        String paymentSessionId
) {
}
