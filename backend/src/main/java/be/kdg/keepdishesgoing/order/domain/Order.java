package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.common.events.DomainEvent;
import be.kdg.keepdishesgoing.common.events.order.OrderPublishEvent;
import be.kdg.keepdishesgoing.order.domain.vo.OrderId;

import java.util.ArrayList;
import java.util.List;

public class Order {
    
    private OrderId orderId;
    private Basket basket;
    private Customer customer;
    private String message;
    
    private final List<DomainEvent> domainEvents = new ArrayList<>();


    private Order(OrderId orderId, Basket basket, Customer customer) {
        this.orderId = orderId;
        this.basket = basket;
        this.customer = customer;
    }

    public static Order createNew(Basket basket, Customer customer) {
        Order order = new Order(OrderId.create(), basket, customer);

        List<OrderPublishEvent.OrderLine> lines = basket.getItems().stream()
                .map(item -> new OrderPublishEvent.OrderLine(
                        item.getDishId().dishId(),
                        item.getPriceAtAddition(),
                        item.getQuantity()
                ))
                .toList();

        OrderPublishEvent event = new OrderPublishEvent(
                order.getOrderId().orderId(),
                
                customer.getDeliveryAddress().street(),
                customer.getDeliveryAddress().number(),
                customer.getDeliveryAddress().postalCode(),
                customer.getDeliveryAddress().city(),
                customer.getDeliveryAddress().country(),
                
                basket.getRestaurantId().restaurantId(),
                lines,
                basket.getTotalPrice()
        );

        order.domainEvents.add(event);

        return order;
    }

    
    public static Order rehydrate(OrderId orderId, Basket basket, Customer customer) {
        return new Order(orderId, basket, customer);
        
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
    
    public void clearDomainEvents(){
        domainEvents.clear();
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Basket getBasket() {
        return basket;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
