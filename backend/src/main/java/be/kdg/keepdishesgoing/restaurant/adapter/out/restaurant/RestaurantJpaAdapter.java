package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurant;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.restaurant.domain.*;
import be.kdg.keepdishesgoing.restaurant.domain.vo.*;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.PersistRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.UpdateRestaurantPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantJpaAdapter implements LoadRestaurantPort, PersistRestaurantPort, UpdateRestaurantPort {

    private final RestaurantJpaRepository restaurantJpaRepository;

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantJpaRepository) {
        this.restaurantJpaRepository = restaurantJpaRepository;
    }


    @Override
    public List<Restaurant> loadAll() {
        return restaurantJpaRepository.findAll()
                .stream()
                .map(entity -> Restaurant.rehydrate(
                        RestaurantId.of(entity.getId()),
                        OwnerId.of(entity.getOwnerId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        Address.of(entity.getStreet(), entity.getNumber(), entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        entity.getCuisineType(),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        entity.getPriceRange()
                )).toList();
    }
    
    @Override
    public Optional<Restaurant> loadRestaurantByName(String name) {
        return restaurantJpaRepository.findByName(name)
                .map(entity -> Restaurant.rehydrate(
                        RestaurantId.of(entity.getId()),
                        OwnerId.of(entity.getOwnerId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        Address.of(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        entity.getCuisineType(),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        entity.getPriceRange()
                ));
    }

    @Override
    public List<Restaurant> loadAllByType(CuisineType type) {
        return restaurantJpaRepository.findByCuisineType(type)
                .stream()
                .map(entity -> Restaurant.rehydrate(
                        RestaurantId.of(entity.getId()),
                        OwnerId.of(entity.getOwnerId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        Address.of(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        entity.getCuisineType(),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        entity.getPriceRange()
                )).toList();
    }

    @Override
    public List<Restaurant> loadAllByPriceRange(String priceRange) {
        return restaurantJpaRepository.findRestaurantJpaEntitiesByPriceRange(PriceRange.valueOf(priceRange))
                .stream()
                .map(entity -> Restaurant.rehydrate(
                        RestaurantId.of(entity.getId()),
                        OwnerId.of(entity.getOwnerId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        Address.of(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        entity.getCuisineType(),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        entity.getPriceRange()
                ))
                .toList();
    }
    

    @Override
    public Optional<Restaurant> loadRestaurantById(UUID restaurantId) {
        return restaurantJpaRepository.findRestaurantJpaEntitiesById(restaurantId)
                .map(entity -> Restaurant.rehydrate(
                        RestaurantId.of(entity.getId()),
                        OwnerId.of(entity.getOwnerId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        Address.of(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        entity.getCuisineType(),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        entity.getPriceRange()
                ));
                
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantJpaEntity entity = RestaurantJpaMapper.fromDomain(restaurant);
        RestaurantJpaEntity saved = restaurantJpaRepository.save(entity);
        return RestaurantJpaMapper.toDomain(saved);
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity entity = RestaurantJpaMapper.fromDomain(restaurant);
        RestaurantJpaEntity saved = restaurantJpaRepository.save(entity);
        return RestaurantJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Restaurant> loadRestaurantByOwnerId(OwnerId ownerId) {
        return restaurantJpaRepository.findRestaurantJpaEntitiesByOwnerId(ownerId.ownerId())
                .map(RestaurantJpaMapper::toDomain);
    }
}
