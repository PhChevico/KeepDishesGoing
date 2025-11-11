package be.kdg.keepdishesgoing.restaurant.domain;

import be.kdg.keepdishesgoing.common.events.DomainEvent;
import be.kdg.keepdishesgoing.common.events.order.*;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OrderId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOrder {
    private OrderId orderId;
    private Address deliveryAddress;
    private RestaurantId restaurantId;
    private BigDecimal totalPrice;
    private List<OrderLine> orderLines;
    private OrderStatus status;
    
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public RestaurantOrder(OrderId orderId, Address deliveryAddress, RestaurantId restaurantId, BigDecimal totalPrice, List<OrderLine> orderLines) {
        this.orderId = orderId;
        this.deliveryAddress = deliveryAddress;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
        this.status = OrderStatus.RECEIVED;
    }
    

    public OrderId getOrderId() {
        return orderId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void accept(Address pickupAddress, Coordinates pickupCoordinates, Coordinates dropoffCoordinates) {
        this.status = OrderStatus.ACCEPTED;

        addDomainEvents(new OrderAcceptedEvent(
                this.getRestaurantId().restaurantId(),
                this.getOrderId().orderId(),
                pickupAddress,
                pickupCoordinates,
                this.getDeliveryAddress(),
                dropoffCoordinates
        ));
    }

    
    public void refuse(String message) {
        if (this.status == OrderStatus.REFUSED) {
            throw new IllegalArgumentException("Order has already been refused");
        }
        
        this.status = OrderStatus.REFUSED;
        
        addDomainEvents(new OrderRefuseEvent(
                this.getOrderId().orderId(), 
                message
        ));
        
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
    
    public void addDomainEvents(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }
    
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public void readyForPickUp() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalArgumentException("Order wasn't accepted");
        }
        
        this.status = OrderStatus.READY_FOR_PICK_UP;
        
        this.domainEvents.add(new OrderReadyForPickupEvent(
                this.restaurantId.restaurantId(),
                this.orderId.orderId()
        ));
        
    }
    
    public void completed(){
        this.status = OrderStatus.COMPLETED;
    }
}

