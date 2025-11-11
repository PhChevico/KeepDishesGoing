package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.math.BigDecimal;
import java.util.List;

public class DishProjection {
    private DishId id;
    private String name;
    private TypeOfDishProjection typeOfDish;
    private List<FoodTagProjection> foodTagList;
    private String description;
    private BigDecimal price;
    private String picture;
    private DishStatusProjection dishStatus;
    private FoodMenuId foodMenuId;
    private RestaurantId restaurantId;

    public DishProjection(DishId id, String name, TypeOfDishProjection typeOfDish, List<FoodTagProjection> foodTagList,
                          String description, BigDecimal price, String picture, DishStatusProjection dishStatus,
                          FoodMenuId foodMenuId, RestaurantId restaurantId) {
        this.id = id;
        this.name = name;
        this.typeOfDish = typeOfDish;
        this.foodTagList = foodTagList;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.dishStatus = dishStatus;
        this.foodMenuId = foodMenuId;
        this.restaurantId = restaurantId;
    }

    public void setDishOutOfStock(){
        if(this.dishStatus == DishStatusProjection.OUT_OF_STOCK){
            throw new IllegalStateException("Dish is already out-of-stock.");
        }

        this.dishStatus = DishStatusProjection.OUT_OF_STOCK;
    }

    public void setDishInStock(){
        if(this.dishStatus != DishStatusProjection.OUT_OF_STOCK){
            throw new IllegalStateException("Dish is already in stock.");
        }

        this.dishStatus = DishStatusProjection.PUBLISHED;
    }
    
    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public DishId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TypeOfDishProjection getTypeOfDish() {
        return typeOfDish;
    }

    public List<FoodTagProjection> getFoodTagList() {
        return foodTagList;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public DishStatusProjection getDishStatus() {
        return dishStatus;
    }

    public FoodMenuId getFoodMenuId() {
        return foodMenuId;
    }

}
