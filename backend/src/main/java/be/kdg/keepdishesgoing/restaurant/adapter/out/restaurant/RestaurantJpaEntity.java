package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurant;

import be.kdg.keepdishesgoing.restaurant.domain.CuisineType;
import be.kdg.keepdishesgoing.restaurant.domain.PriceRange;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "restaurant", schema = "restaurant")
public class RestaurantJpaEntity {
    
    @Id
    private UUID id;
    @Column
    private UUID ownerId;
    @Column
    private String name;
    @Column
    private String email;
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
    @Enumerated(EnumType.STRING)
    @Column
    private CuisineType cuisineType;
    @Column
    private int defaultTimePreparation;
    @Column
    private String picture;
    @Enumerated(EnumType.STRING)
    @Column
    private PriceRange priceRange;
    
    public RestaurantJpaEntity() {
        this.id = UUID.randomUUID();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getDefaultTimePreparation() {
        return defaultTimePreparation;
    }

    public void setDefaultTimePreparation(int defaultTimePreparation) {
        this.defaultTimePreparation = defaultTimePreparation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }
}
