package be.kdg.keepdishesgoing.order.adapter.in.request;

public record CheckoutBasketRequest (
        String firstName,
        String lastName,
        String street,
        Integer number,
        Integer postalCode,
        String city,
        String country,
        String emailAddress
){
}
