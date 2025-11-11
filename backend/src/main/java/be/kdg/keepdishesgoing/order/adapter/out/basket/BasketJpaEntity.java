package be.kdg.keepdishesgoing.order.adapter.out.basket;

import be.kdg.keepdishesgoing.order.domain.BasketStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "baskets", schema = "orders")
public class BasketJpaEntity {
    
    
    @Id
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BasketItemJpaEntity> items = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "basket_status")
    private BasketStatus basketStatus;

    @Column(name = "message")
    private String message;

    @Column(name = "order_id", unique = true)
    private UUID orderId;


    @Column(nullable = false)
    private UUID anonymousId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "payment_session_id", columnDefinition = "TEXT")
    private String paymentSessionId;


    public BasketJpaEntity() {}


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPaymentSessionId() {
        return paymentSessionId;
    }

    public void setPaymentSessionId(String paymentSessionId) {
        this.paymentSessionId = paymentSessionId;
    }

    public UUID getAnonymousId() {
        return anonymousId;
    }
    public void setAnonymousId(UUID anonymousId) {
        this.anonymousId = anonymousId;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getRestaurantId() { return restaurantId; }
    public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public List<BasketItemJpaEntity> getItems() { return items; }
    public void setItems(List<BasketItemJpaEntity> items) {
        this.items = items;
        for (BasketItemJpaEntity item : items) {
            item.setBasket(this);
        }
    }

    public BasketStatus getBasketStatus() {
        return basketStatus;
    }

    public void setBasketStatus(BasketStatus basketStatus) {
        this.basketStatus = basketStatus;
    }
}