package be.kdg.keepdishesgoing.restaurant.domain.exceptions;

public class RestaurantOwnerNotFound extends RuntimeException {
    public RestaurantOwnerNotFound(String message) {
        super(message);
    }
}
