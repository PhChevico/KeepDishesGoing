package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_lines", schema = "restaurant")
public class OrderLineJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID dishId;

    private BigDecimal priceAtAddition;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private RestaurantOrderJpaEntity restaurantOrder;

    protected OrderLineJpaEntity() {
    }

    public OrderLineJpaEntity(UUID dishId, BigDecimal priceAtAddition, int quantity) {
        this.dishId = dishId;
        this.priceAtAddition = priceAtAddition;
        this.quantity = quantity;
    }

    public void setRestaurantOrder(RestaurantOrderJpaEntity restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public void setPriceAtAddition(BigDecimal priceAtAddition) {
        this.priceAtAddition = priceAtAddition;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public RestaurantOrderJpaEntity getRestaurantOrder() {
        return restaurantOrder;
    }
}
