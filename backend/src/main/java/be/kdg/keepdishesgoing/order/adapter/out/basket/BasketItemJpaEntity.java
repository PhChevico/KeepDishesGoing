package be.kdg.keepdishesgoing.order.adapter.out.basket;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "basket_items", schema = "orders")
public class BasketItemJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(name = "dish_name")
    private String dishName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private BasketJpaEntity basket;

    @Column(name = "dish_id", nullable = false)
    private UUID dishId;

    @Column(name = "price_at_addition", nullable = false)
    private BigDecimal priceAtAddition;

    @Column(nullable = false)
    private int quantity;
    
    
    public  BasketItemJpaEntity() {}
    

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BasketJpaEntity getBasket() { return basket; }
    public void setBasket(BasketJpaEntity basket) { this.basket = basket; }

    public UUID getDishId() { return dishId; }
    public void setDishId(UUID dishId) { this.dishId = dishId; }

    public BigDecimal getPriceAtAddition() { return priceAtAddition; }
    public void setPriceAtAddition(BigDecimal priceAtAddition) { this.priceAtAddition = priceAtAddition; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
