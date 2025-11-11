package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.order.domain.vo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Basket {
    private final BasketId basketId;
    private RestaurantId restaurantId;
    private List<BasketItem> items = new ArrayList<>();
    private BigDecimal totalPrice;
    private BasketStatus basketStatus;
    private OrderId orderId;
    private String message;
    private AnonymousId anonymousId;

    private String firstName;
    private String lastName;
    private String street;
    private Integer number;
    private Integer postalCode;
    private String city;
    private String country;
    private String emailAddress;

    private String paymentSessionId;

    private Basket(BasketId basketId, RestaurantId restaurantId, List<BasketItem> items, BigDecimal totalPrice,  BasketStatus basketStatus, String message,  AnonymousId anonymousUserId, String paymentSessionId) {
        this.basketId = basketId;
        this.restaurantId = restaurantId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.basketStatus = basketStatus;
        this.message = message;
        this.anonymousId = anonymousUserId;
        this.paymentSessionId = paymentSessionId;
    }

    public static Basket createNew(RestaurantId restaurantId, AnonymousId anonymousId) {
        return new Basket(
                BasketId.create(),            // new unique basketId
                restaurantId,                 // restaurantId from parameter
                new ArrayList<>(),            // empty list of items
                BigDecimal.ZERO,                           // initial totalPrice
                BasketStatus.ACTIVE,
                null,
                anonymousId,
                null
        );
    }

    public static Basket rehydrate(BasketId basketId, RestaurantId restaurantId,
                                   List<BasketItem> items, BigDecimal totalPrice,
                                   BasketStatus basketStatus, String message,
                                   AnonymousId anonymousUserId, String paymentSessionId) {
        return new Basket(
                basketId, restaurantId, items, totalPrice, basketStatus, message,
                anonymousUserId, paymentSessionId
        );
    }



    public void addDish(DishProjection dish, int quantity) {
        if (dish.getDishStatus() != DishStatusProjection.PUBLISHED) {
            throw new IllegalStateException("Cannot add unpublished dish to basket");
        } 
        
        if (this.getBasketStatus() != BasketStatus.ACTIVE) {
            throw new IllegalStateException("Cannot add to ordered basket");
        }

        if (restaurantId != null && restaurantId.equals(dish.getRestaurantId().restaurantId())) {
            throw new IllegalStateException("Basket can only contain dishes from a single restaurant");
        }

        if (restaurantId == null) {
            restaurantId = dish.getRestaurantId();
        }

        // Check if dish is already in basket
        BasketItem existingItem = items.stream()
                .filter(i -> i.getDishId().equals(dish.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.incrementQuantity(quantity);
        } else {
            items.add(new BasketItem(dish.getId(), dish.getName(), dish.getPrice(), quantity));
        }

        updateTotalPrice();
    }


    public AnonymousId getAnonymousId() {
        return anonymousId;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPaymentSessionId() {
        return paymentSessionId;
    }
    
    

    public void removeDish(DishId dishId, int quantity) {
        if(basketStatus != BasketStatus.ACTIVE) {
            throw new IllegalStateException("Cannot remove unpublished dish from basket");
        }
        
        BasketItem existingItem = items.stream()
                .filter(i -> i.getDishId().equals(dishId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dish not found in basket"));

        if (quantity >= existingItem.getQuantity()) {
            items.remove(existingItem);
        } else {
            existingItem.decrementQuantity(quantity);
        }

        updateTotalPrice();
    }


    private void updateTotalPrice() {
        totalPrice = items.stream()
                .map(BasketItem::totalPrice)                  // BigDecimal stream
                .reduce(BigDecimal.ZERO, BigDecimal::add);   // sum safely
    }


    public BasketId getBasketId() {
        return basketId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BasketStatus getStatus() {
        return basketStatus;
    }
    
    public void markAsOrdered(){
        if (isOrdered()){
            throw new IllegalStateException("Basket has already been ordered.");
        }
        this.basketStatus = BasketStatus.ORDERED;
    }

    public boolean isOrdered() {
        return basketStatus == BasketStatus.ORDERED;
    }

    public boolean isAccepted() {
        return basketStatus == BasketStatus.ACCEPTED;
    }
    
    public void markAsAccepted(){
        if (isAccepted()){
            throw new IllegalStateException("Order already accepted.");
        }
        
        this.basketStatus = BasketStatus.ACCEPTED;
    }


    public void markAsRefused() {
        if (isAccepted()){
            throw new IllegalStateException("Order was already accepted");
        }

        this.basketStatus = BasketStatus.REFUSED;
    }

    public void markAsReadyForPickup() {
        if (!isAccepted()) {
            throw new IllegalStateException("Order must be accepted before it can be marked ready for pickup.");
        }
        this.basketStatus = BasketStatus.READY_FOR_PICKUP;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public BasketStatus getBasketStatus() {
        return basketStatus;
    }

    public void addMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void markAsPickedUp() {
        if (this.basketStatus != BasketStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Order's not ready for pick up");
        }
        this.basketStatus = BasketStatus.PICKED_UP;
    }

    public void markAsDelivered() {
        if (this.basketStatus != BasketStatus.PICKED_UP) {
            throw new IllegalStateException("Order hasn't been picked up yet");
        }
        this.basketStatus = BasketStatus.DELIVERED;
    }

    public void checkout(String firstName, String lastName, String street, Integer number, Integer postalCode, String city, String country, String emailAddress){

        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.emailAddress = emailAddress;
    }

    public void assignPaymentSession(String sessionId) {
        this.paymentSessionId = sessionId;
    }
    
    public void setAddress(String street, Integer number, Integer postalCode, String city, String country) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public boolean isPickedUp(){
        return this.basketStatus == BasketStatus.PICKED_UP;
    }

    public boolean isReadyForPickup() {
        return this.basketStatus == BasketStatus.READY_FOR_PICKUP;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
