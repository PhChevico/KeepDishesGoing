package be.kdg.keepdishesgoing.restaurant.adapter.out.dish;

import be.kdg.keepdishesgoing.restaurant.adapter.out.foodMenu.FoodMenuJpaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import be.kdg.keepdishesgoing.restaurant.domain.DishStatus;
import be.kdg.keepdishesgoing.restaurant.domain.TypeOfDish;
import be.kdg.keepdishesgoing.restaurant.adapter.out.dishFoodTags.DishFoodTagJpaEntity;

@Entity
@Table(name = "dishes", schema = "restaurant")
public class DishJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeOfDish typeOfDish;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DishFoodTagJpaEntity> foodTags = new HashSet<>();

    private String description;
    private BigDecimal price;
    private String picture;

    @Enumerated(EnumType.STRING)
    private DishStatus dishStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_menu_id")
    private FoodMenuJpaEntity foodMenu;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    // Constructors
    public DishJpaEntity() {}

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TypeOfDish getTypeOfDish() { return typeOfDish; }
    public void setTypeOfDish(TypeOfDish typeOfDish) { this.typeOfDish = typeOfDish; }

    public Set<DishFoodTagJpaEntity> getFoodTags() { return foodTags; }
    public void setFoodTags(Set<DishFoodTagJpaEntity> foodTags) { this.foodTags = foodTags; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public DishStatus getDishStatus() { return dishStatus; }
    public void setDishStatus(DishStatus dishStatus) { this.dishStatus = dishStatus; }

    public FoodMenuJpaEntity getFoodMenu() { return foodMenu; }
    public void setFoodMenu(FoodMenuJpaEntity foodMenuId) { this.foodMenu = foodMenuId; }

    public UUID getRestaurantId() { return restaurantId; }
    public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }
}
