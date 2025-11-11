package be.kdg.keepdishesgoing.order.adapter.out.restaurant;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "restaurant_projection", schema = "orders")
public class RestaurantProjectionJpaEntity {

    @Id
    private UUID id;

    private String name;
    private String email;
    private String street;
    private int number;
    private int postalCode;
    private String city;
    private String country;
    private String cuisineType;
    private int defaultTimePreparation;
    private String picture;
    private String priceRange;

    protected RestaurantProjectionJpaEntity() {
    }

    public RestaurantProjectionJpaEntity(
            UUID id,
            String name,
            String email,
            String street,
            int number,
            int postalCode,
            String city,
            String country,
            String cuisineType,
            int defaultTimePreparation,
            String picture,
            String priceRange
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.cuisineType = cuisineType;
        this.defaultTimePreparation = defaultTimePreparation;
        this.picture = picture;
        this.priceRange = priceRange;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getStreet() { return street; }
    public int getNumber() { return number; }
    public int getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getCuisineType() { return cuisineType; }
    public int getDefaultTimePreparation() { return defaultTimePreparation; }
    public String getPicture() { return picture; }
    public String getPriceRange() { return priceRange; }

    public void setId(UUID restaurantId) {
        this.id = restaurantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setDefaultTimePreparation(int defaultTimePreparation) {
        this.defaultTimePreparation = defaultTimePreparation;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
}
