package be.kdg.keepdishesgoing.common.events.restaurant;

import be.kdg.keepdishesgoing.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestaurantCreatedEvent(LocalDateTime eventPit, UUID restaurantUUID, String name, String email,     
                                     String street, int number, int postalCode, String city, String country, 
                                     String cuisineType, int defaultTimeOfPreparation, String picture,
                                     String priceRange) implements DomainEvent {


    public RestaurantCreatedEvent(UUID restaurantUUID, String name, String email,     
                                  String street, int number, int postalCode, String city, 
                                  String country, String cuisineType, int defaultTimeOfPreparation, 
                                  String picture, String priceRange) {
        
        this(LocalDateTime.now(), restaurantUUID, name, email, street, number, postalCode, city, country, cuisineType, 
                defaultTimeOfPreparation, picture, priceRange);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }


}
