package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.order.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

public class FoodMenu {
    
    private FoodMenuId foodMenuId;
    private RestaurantId restaurantId;

    public FoodMenu(FoodMenuId foodMenuId, RestaurantId restaurantId) {
        this.foodMenuId = foodMenuId;
        this.restaurantId = restaurantId;
    }

    public FoodMenuId getFoodMenuId() {
        return foodMenuId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

}
