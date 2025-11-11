package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OwnerId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.Picture;
import be.kdg.keepdishesgoing.restaurant.domain.vo.StreetAddress;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.CreateRestaurantCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.PersistRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.RestaurantEventPublisherPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {


    private final PersistRestaurantPort persistRestaurantPort;
    private final RestaurantEventPublisherPort restaurantEventPublisherPort;
    private final LoadRestaurantPort loadRestaurantPort;
    
    public CreateRestaurantUseCaseImpl(PersistRestaurantPort persistRestaurantPort, RestaurantEventPublisherPort restaurantEventPublisherPort, LoadRestaurantPort loadRestaurantPort) {
        this.persistRestaurantPort = persistRestaurantPort;
        this.restaurantEventPublisherPort = restaurantEventPublisherPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantCommand createRestaurantCommand) {
        OwnerId ownerId = OwnerId.of(createRestaurantCommand.ownerId());

        loadRestaurantPort.loadRestaurantByOwnerId(ownerId).ifPresent(existing -> {
            throw new IllegalStateException("Owner already has a restaurant: " + existing.getName());
        });
        
        
        Restaurant restaurant = Restaurant.createNew(
                OwnerId.of(createRestaurantCommand.ownerId()),
                createRestaurantCommand.name(),
                EmailAddress.of(createRestaurantCommand.email()),
                Address.of(
                        createRestaurantCommand.street(),
                        createRestaurantCommand.number(),
                        createRestaurantCommand.postalCode(),
                        createRestaurantCommand.city(),
                        createRestaurantCommand.country()
                ),
                createRestaurantCommand.cuisineType(),
                createRestaurantCommand.defaultTimePreparation(),
                Picture.of(createRestaurantCommand.picture()),
                createRestaurantCommand.priceRange()
        );

        persistRestaurantPort.save(restaurant);

        restaurantEventPublisherPort.publishDomainEvents(restaurant);
        return restaurant;
    }
}
