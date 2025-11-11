package be.kdg.keepdishesgoing.order.adapter.in.response.basket;

public record AddressDto(
        String street,
        Integer number,
        Integer postalCode,
        String city,
        String country
) {
}