package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import be.kdg.keepdishesgoing.restaurant.domain.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurant_orders", schema = "restaurant")
public class RestaurantOrderJpaEntity {

    @Id
    private UUID orderId;

    @Column
    private String street;
    @Column
    private int number;
    @Column
    private int postalCode;
    @Column
    private String city;
    @Column
    private String country;

    private UUID restaurantId;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "restaurantOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineJpaEntity> orderLines;

    // epoch millis when the order was created
    @Column(name = "created_at")
    private long createdAtEpochMillis;

    protected RestaurantOrderJpaEntity() {
    }

    public RestaurantOrderJpaEntity(UUID orderId, String street, int number, int postalCode, String city,
                                    String country, UUID restaurantId, BigDecimal totalPrice,
                                    OrderStatus status, List<OrderLineJpaEntity> orderLines) {
        this.orderId = orderId;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderLines = orderLines;

        // set creation time when newly constructed if not already set
        if (this.createdAtEpochMillis == 0L) {
            this.createdAtEpochMillis = System.currentTimeMillis();
        }

        this.orderLines.forEach(line -> line.setRestaurantOrder(this));
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
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

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderLineJpaEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineJpaEntity> orderLines) {
        this.orderLines = orderLines;
    }

    public long getCreatedAtEpochMillis() {
        return createdAtEpochMillis;
    }

    public void setCreatedAtEpochMillis(long createdAtEpochMillis) {
        this.createdAtEpochMillis = createdAtEpochMillis;
    }
}
