package be.kdg.keepdishesgoing.order.domain.vo;

public record CustomerName (
        String firstName,
        String lastName
) {
    
    public static CustomerName of(String firstName, String lastName) {
        return new CustomerName(firstName, lastName);
    }
    
}
