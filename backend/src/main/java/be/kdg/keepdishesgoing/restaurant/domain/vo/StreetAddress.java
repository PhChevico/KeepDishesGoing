 package be.kdg.keepdishesgoing.restaurant.domain.vo;

    public record StreetAddress(
            String street,
            int number,
            int postalCode,
            String city,
            String country
    ) {

        public StreetAddress {
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

        public static StreetAddress of(String street, int number, int postalCode, String city, String country) {
            return new StreetAddress(street, number, postalCode, city, country);
        }

    }
