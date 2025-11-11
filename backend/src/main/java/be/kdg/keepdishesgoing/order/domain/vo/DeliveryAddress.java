package be.kdg.keepdishesgoing.order.domain.vo;

public record DeliveryAddress (
        String street,
        Integer number,
        Integer postalCode,
        String city,
        String country
) {

    public DeliveryAddress {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        if (postalCode <= 0) {
            throw new IllegalArgumentException("Postal code must be positive");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank");
        }
    }

    public static DeliveryAddress of(String street, int number, int postalCode, String city, String country) {
        return new DeliveryAddress(street, number, postalCode, city, country);
    }
}
