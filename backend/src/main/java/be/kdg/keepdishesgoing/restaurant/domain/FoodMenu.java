package be.kdg.keepdishesgoing.restaurant.domain;

import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class FoodMenu {

    public static final int LIMIT_DISHES = 10;
    private FoodMenuId foodMenuId;
    private RestaurantId restaurantId;
    private List<Dish> dishes = new ArrayList<>();
    private BigDecimal averagePrice;
    
    private FoodMenu(FoodMenuId foodMenuId,  RestaurantId restaurantId, BigDecimal averagePrice) {
        this.foodMenuId = foodMenuId;
        this.restaurantId = restaurantId;
        this.averagePrice = averagePrice;
    }
    
    
    public static FoodMenu createNew(RestaurantId restaurantId){
        return new FoodMenu(
                FoodMenuId.create(),
                restaurantId,
                BigDecimal.ZERO
        );
    }
    
    public static FoodMenu rehydrate(FoodMenuId foodMenuId,  RestaurantId restaurantId, BigDecimal averagePrice){
        return new FoodMenu(
                foodMenuId,
                restaurantId,
                averagePrice);
    }

    public void addDish(Dish dish) {
        if (!(dishes instanceof ArrayList)) {
            dishes = new ArrayList<>(dishes != null ? dishes : List.of());
        }

        boolean replaced = false;
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getDishId().equals(dish.getDishId())) {
                dishes.set(i, dish);
                replaced = true;
                break;
            }
        }

        if (!replaced) {
            if (dishes.size() >= LIMIT_DISHES) {
                throw new IllegalStateException("Menu can only have 10 dishes");
            }
            dishes.add(dish);
        }

        dish.setFoodMenuId(this.foodMenuId);
        dish.setDishPublish(); // fix this publishDish
        this.updateAverage();
    }


    public void removeDish(Dish dish) {
        if(dishes.contains(dish)){
            dishes.remove(dish);
            dish.setFoodMenuId(null);
            dish.setDishUnpublish();
            this.updateAverage();
        }
    }

    public void updateAverage() {
        if (dishes == null || dishes.isEmpty()) {
            this.averagePrice = BigDecimal.ZERO; // or null if you prefer
            return;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (Dish dish : dishes) {
            sum = sum.add(dish.getPrice());
        }

        this.averagePrice = sum.divide(
                BigDecimal.valueOf(dishes.size()),
                2,                 // scale: 2 decimal places
                RoundingMode.HALF_UP
        );
    }


    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public FoodMenuId getFoodMenuId() {
        return foodMenuId;
    }


    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

}
