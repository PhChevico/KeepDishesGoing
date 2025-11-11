package be.kdg.keepdishesgoing.order.adapter.in.response.basket;

public record ClientInfoDto(
        String firstName,
        String lastName,
        String emailAddress
) {
}