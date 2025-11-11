package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.order.domain.vo.DishId;

import java.math.BigDecimal;

public class BasketItem {
    private DishId dishId;
    private String dishName;
    private BigDecimal priceAtAddition;
    private int quantity;

    public BasketItem(DishId dishId, String dishName, BigDecimal priceAtAddition, int quantity) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.priceAtAddition = priceAtAddition;
        this.quantity = quantity;
    }

    public void incrementQuantity(int qty) {
        this.quantity += qty;
    }
    
    public void decrementQuantity(int qty) {
        this.quantity -= qty;
    }

    public BigDecimal totalPrice() {
        return priceAtAddition.multiply(BigDecimal.valueOf(quantity));
    }

    public String getDishName() {
        return dishName;
    }

    public DishId getDishId() {
        return dishId;
    }

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public int getQuantity() { return quantity; }
}
