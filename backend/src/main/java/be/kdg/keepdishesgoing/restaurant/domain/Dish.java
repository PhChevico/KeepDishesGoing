package be.kdg.keepdishesgoing.restaurant.domain;

import be.kdg.keepdishesgoing.common.events.dish.DishPublishEvent;
import be.kdg.keepdishesgoing.common.events.dish.DishUnpublishEvent;
import be.kdg.keepdishesgoing.common.events.DomainEvent;
import be.kdg.keepdishesgoing.common.events.dish.MarkDishOutOfStockEvent;
import be.kdg.keepdishesgoing.common.events.dish.MarkDishInStockEvent;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dish {
    private DishId dishId;
    private String name;
    private TypeOfDish typeOfDish;
    private List<FoodTag> foodTagList;
    private String description;
    private BigDecimal price;
    private String picture;
    private DishStatus dishStatus;
    private FoodMenuId foodMenuId;
    private RestaurantId restaurantId;

    private final List<DomainEvent> domainEvents = new ArrayList<>();



    private Dish(DishId dishId, String name, TypeOfDish typeOfDish, List<FoodTag> foodTagList, String description,
                 BigDecimal price, String picture, DishStatus dishStatus, RestaurantId restaurantId) {
        this.dishId = dishId;
        this.name = name;
        this.typeOfDish = typeOfDish;
        this.foodTagList = foodTagList;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.dishStatus = dishStatus;
        this.restaurantId = restaurantId;
    }
    
    
    public static Dish createNew(String name,
                          TypeOfDish typeOfDish,
                          List<FoodTag> foodTagList,
                          String description,
                          BigDecimal price,
                          String picture,
                          RestaurantId restaurantId){
        
        
        
        return new Dish(
                DishId.create(),
                name,
                typeOfDish,
                foodTagList,
                description,
                price,
                picture,
                DishStatus.UNPUBLISHED,
                restaurantId
        );
        
    }
    
    public static Dish rehydrate(
            DishId dishId, String name, TypeOfDish typeOfDish,
            List<FoodTag> foodTagList, String description,
            BigDecimal price, String picture, DishStatus dishStatus,
            RestaurantId restaurantId
    ){
        
        return new Dish(dishId, name, typeOfDish,  foodTagList, description, price, picture, dishStatus, restaurantId);
        
    }


    public void publishToMenu(FoodMenu menu) {
        if (this.dishStatus == DishStatus.PUBLISHED) {
            throw new IllegalStateException("Dish is already published.");
        }

        menu.addDish(this); // menu enforces 10-dish rule
        this.dishStatus = DishStatus.PUBLISHED;
        this.foodMenuId = menu.getFoodMenuId();

        addDomainEvent(new DishPublishEvent(
                this.dishId.dishId(),
                this.name,
                this.typeOfDish.name(),
                this.foodTagList.stream().map(Enum::name).toList(),
                this.description,
                this.price,
                this.picture,
                this.dishStatus.name(),
                this.foodMenuId.foodMenuId(),
                this.restaurantId.restaurantId()
        ));
    }


    public void unpublishFromMenu(FoodMenu menu) {
        if (this.dishStatus == DishStatus.UNPUBLISHED) {
            throw new IllegalStateException("Dish is not currently published.");
        }

        menu.removeDish(this);
        this.dishStatus = DishStatus.UNPUBLISHED;
        this.foodMenuId = null;

        addDomainEvent(new DishUnpublishEvent(
                this.dishId.dishId(),
                this.name,
                this.typeOfDish.name(),
                this.foodTagList.stream().map(Enum::name).toList(),
                this.description,
                this.price,
                this.picture,
                this.dishStatus.name(),
                null,
                this.restaurantId.restaurantId()
        ));
    }
    
    public void setDishOutOfStock(){
        if(this.dishStatus == DishStatus.OUT_OF_STOCK){
            throw new IllegalStateException("Dish is already out-of-stock.");
        }
        
        this.dishStatus = DishStatus.OUT_OF_STOCK;
        
        this.addDomainEvent(new MarkDishOutOfStockEvent(
                this.getDishId().dishId()
        ));
    }

    public void setDishInStock(){
        if(this.dishStatus != DishStatus.OUT_OF_STOCK){
            throw new IllegalStateException("Dish is already in stock.");
        }

        this.dishStatus = DishStatus.PUBLISHED;

        this.addDomainEvent(new MarkDishInStockEvent(
                this.getDishId().dishId()
        ));
    }

    public void updateDetails(
            String name,
            TypeOfDish typeOfDish,
            List<FoodTag> foodTags,
            String description,
            BigDecimal price,
            String picture,
            RestaurantId restaurantId
    ) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }


        this.name = name;
        this.typeOfDish = typeOfDish;
        this.foodTagList = foodTags;
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.setDishAsDraft();
        this.restaurantId = restaurantId;
    }

    public void setDishPublish(){
        this.dishStatus = DishStatus.PUBLISHED;

        this.addDomainEvent(new DishPublishEvent(
                this.dishId.dishId(),
                this.name,
                this.typeOfDish.name(),
                this.foodTagList.stream().map(Enum::name).toList(),
                this.description,
                this.price,
                this.picture,
                this.dishStatus.name(),
                this.foodMenuId.foodMenuId(),
                this.restaurantId.restaurantId()
        ));
        
    }
    
    
    public void setDishUnpublish(){
        this.dishStatus = DishStatus.UNPUBLISHED;
    }
    

    public void setDishAsDraft(){
        this.dishStatus = DishStatus.DRAFT;
    }


    public DishStatus getDishStatus() {
        return dishStatus;
    }

    public DishId getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public TypeOfDish getTypeOfDish() {
        return typeOfDish;
    }

    public List<FoodTag> getFoodTagList() {
        return foodTagList;
    }

    public void setFoodTagList(List<FoodTag> foodTagList) {
        this.foodTagList = foodTagList;
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

    public FoodMenuId getFoodMenuId() {
        return foodMenuId;
    }

    public void setFoodMenuId(FoodMenuId foodMenuId) {
        this.foodMenuId = foodMenuId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public void addDomainEvent(DomainEvent domainEvent){
        domainEvents.add(domainEvent);
    }
    
    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
