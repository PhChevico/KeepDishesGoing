package be.kdg.keepdishesgoing.order.adapter.out.dish;

import be.kdg.keepdishesgoing.order.domain.DishStatusProjection;
import be.kdg.keepdishesgoing.order.domain.TypeOfDishProjection;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "dishes", schema = "orders")
public class DishProjectionJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_dish", nullable = false)
    private TypeOfDishProjection typeOfDish;

    @ElementCollection
    @CollectionTable(name = "dish_food_tags", schema = "orders",
            joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "tag")
    private List<String> foodTags = new ArrayList<>();

    private String description;
    private BigDecimal price;
    private String picture;

    @Enumerated(EnumType.STRING)
    private DishStatusProjection dishStatus;

    @Column(name = "food_menu_id")
    private UUID foodMenuId;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    public DishProjectionJpaEntity() {}

    // CHANGED: Updated constructor to use List<String>
    public DishProjectionJpaEntity(UUID id, String name, TypeOfDishProjection typeOfDish,
                                   List<String> foodTags, String description,
                                   BigDecimal price, String picture, DishStatusProjection dishStatus,
                                   UUID foodMenuId, UUID restaurantId) {
        this.id = id;
        this.name = name;
        this.typeOfDish = typeOfDish;
        this.foodTags = foodTags != null ? foodTags : new ArrayList<>();
        this.description = description;
        this.price = price;
        this.picture = picture;
        this.dishStatus = dishStatus;
        this.foodMenuId = foodMenuId;
        this.restaurantId = restaurantId;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeOfDishProjection getTypeOfDish() { return typeOfDish; }
    public void setTypeOfDish(TypeOfDishProjection typeOfDish) { this.typeOfDish = typeOfDish; }

    // CHANGED: Now returns List<String>
    public List<String> getFoodTags() { return foodTags; }

    // CHANGED: Now accepts List<String>
    public void setFoodTags(List<String> foodTags) {
        this.foodTags = foodTags != null ? foodTags : new ArrayList<>();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public DishStatusProjection getDishStatus() { return dishStatus; }
    public void setDishStatus(DishStatusProjection dishStatus) { this.dishStatus = dishStatus; }

    public UUID getFoodMenuId() { return foodMenuId; }
    public void setFoodMenuId(UUID foodMenuId) { this.foodMenuId = foodMenuId; }

    public UUID getRestaurantId() { return restaurantId; }
    public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }
}