package be.kdg.keepdishesgoing.restaurant.adapter.out.foodMenu;

import be.kdg.keepdishesgoing.restaurant.adapter.out.dish.DishJpaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "food_menus", schema = "restaurant")
public class FoodMenuJpaEntity {

    @Id
    private UUID id;
    
    @Column(nullable = false)
    private UUID restaurantId;
    
    @Column
    private BigDecimal averagePrice;

    @OneToMany(mappedBy = "foodMenu", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = false)
    private List<DishJpaEntity> dishes = new ArrayList<>();
    
    public FoodMenuJpaEntity() {
        this.id = UUID.randomUUID();
    }
    
    public FoodMenuJpaEntity(UUID id, UUID restaurantId,  BigDecimal averagePrice) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.averagePrice = averagePrice;
    }

    public List<DishJpaEntity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishJpaEntity> dishes) {
        this.dishes = dishes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID foodMenuId) {
        this.id = foodMenuId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }
}
