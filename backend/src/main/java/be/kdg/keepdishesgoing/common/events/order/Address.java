package be.kdg.keepdishesgoing.common.events.order;

public record Address(String street, int number, int postalCode, String city, String country) {
    
    public static Address of(String street, int number, int postalCode, String city, String country){
        return new Address(street, number, postalCode, city, country);
    }
    
}
