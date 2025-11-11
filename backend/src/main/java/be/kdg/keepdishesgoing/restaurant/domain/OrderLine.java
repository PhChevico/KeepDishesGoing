package be.kdg.keepdishesgoing.restaurant.domain;

import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;

import java.math.BigDecimal;

public class OrderLine {
    private DishId dishId;
    private BigDecimal priceAtAddition;
    private int quantity;

    public OrderLine(DishId dishId, BigDecimal priceAtAddition, int quantity) {
        this.dishId = dishId;
        this.priceAtAddition = priceAtAddition;
        this.quantity = quantity;
    }

    public DishId getDishId() {
        return dishId;
    }

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public int getQuantity() {
        return quantity;
    }
}
