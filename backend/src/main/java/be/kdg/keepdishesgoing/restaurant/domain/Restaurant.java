package be.kdg.keepdishesgoing.restaurant.domain;


import be.kdg.keepdishesgoing.common.events.DomainEvent;
import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.common.events.restaurant.RestaurantCreatedEvent;
import be.kdg.keepdishesgoing.common.events.restaurant.RestaurantPriceRangeUpdatedEvent;
import be.kdg.keepdishesgoing.restaurant.domain.vo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    
    private RestaurantId restaurantId;
    private OwnerId ownerId;
    private String name;
    private EmailAddress email;
    private Address address;
    private CuisineType cuisineType;
    private int defaultTimePreparation;
    private Picture picture;
    private PriceRange priceRange;
    
    private final List<DomainEvent> domainEvents = new ArrayList<>();


    private Restaurant(RestaurantId restaurantId, OwnerId ownerId, String name,
                       EmailAddress email, Address address, CuisineType cuisineType,
                       int defaultTimePreparation, Picture picture, PriceRange priceRange) {
        
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.cuisineType = cuisineType;
        this.defaultTimePreparation = defaultTimePreparation;
        this.picture = picture;
        this.priceRange = priceRange;

    }


    public static Restaurant createNew(OwnerId ownerId, String name, EmailAddress email,
                                       Address address, CuisineType cuisineType,
                                       int defaultTimePreparation, Picture picture,
                                       PriceRange priceRange) {

        Restaurant restaurant = new Restaurant(
                RestaurantId.create(),
                ownerId, name, email, address, cuisineType, defaultTimePreparation, picture, priceRange
        );

        restaurant.domainEvents.add(new RestaurantCreatedEvent(
                restaurant.restaurantId.restaurantId(),
                name,
                email.emailAddress(),
                address.street(),
                address.number(),
                address.postalCode(),
                address.city(),
                address.country(),
                cuisineType.toString(),
                defaultTimePreparation,
                picture.toString(),
                priceRange.toString()
        ));

        return restaurant;
    }

    public static Restaurant rehydrate(RestaurantId restaurantId, OwnerId ownerId, String name,
                                       EmailAddress email, Address address, CuisineType cuisineType,
                                       int defaultTimePreparation, Picture picture, PriceRange priceRange) {
        
        return new Restaurant(restaurantId, ownerId, name, email, address, 
                cuisineType, defaultTimePreparation, picture, priceRange);
    }

    public void updatePriceRange(BigDecimal averageMenuPrice) {
        PriceRange newRange;

        if (averageMenuPrice.compareTo(BigDecimal.valueOf(10)) <= 0) {
            newRange = PriceRange.CHEAP;
        } else if (averageMenuPrice.compareTo(BigDecimal.valueOf(30)) <= 0) {
            newRange = PriceRange.REGULAR;
        } else if (averageMenuPrice.compareTo(BigDecimal.valueOf(60)) <= 0) {
            newRange = PriceRange.EXPENSIVE;
        } else {
            newRange = PriceRange.PREMIUM;
        }

        if (this.priceRange != newRange) { // only publish if changed
            this.priceRange = newRange;
            this.getDomainEvents().add(new RestaurantPriceRangeUpdatedEvent(
                    this.restaurantId.restaurantId(),
                    newRange.toString()
            ));
        }
    }


    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public int getDefaultTimePreparation() {
        return defaultTimePreparation;
    }

    public Picture getPicture() {
        return picture;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
    
    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
