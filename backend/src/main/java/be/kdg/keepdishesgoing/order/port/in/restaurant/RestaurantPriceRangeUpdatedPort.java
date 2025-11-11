package be.kdg.keepdishesgoing.order.port.in.restaurant;


public interface RestaurantPriceRangeUpdatedPort {
    
    void project(RestaurantPriceRangeUpdatedProjectionCommand restaurantPriceRangeUpdatedProjectionCommand);
    
}
