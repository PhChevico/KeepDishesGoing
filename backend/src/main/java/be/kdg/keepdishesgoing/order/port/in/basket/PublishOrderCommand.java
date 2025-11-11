package be.kdg.keepdishesgoing.order.port.in.basket;


import java.util.UUID;

public record PublishOrderCommand(
        UUID basketId,
        String firstName,
        String lastName,
        String street,
        Integer number,
        Integer postalCode,
        String city,
        String country,
        String emailAddress
) {}
