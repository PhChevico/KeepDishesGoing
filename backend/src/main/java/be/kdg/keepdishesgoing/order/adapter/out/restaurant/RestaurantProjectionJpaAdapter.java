package be.kdg.keepdishesgoing.order.adapter.out.restaurant;

import be.kdg.keepdishesgoing.order.adapter.out.restaurant.mapper.RestaurantProjectionMapper;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.order.domain.vo.Picture;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.order.domain.vo.StreetAddress;
import be.kdg.keepdishesgoing.order.port.out.restaurant.LoadRestaurantProjectionPort;
import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class RestaurantProjectionJpaAdapter implements LoadRestaurantProjectionPort {

    private final RestaurantProjectionJpaRepository restaurantJpaRepository;

    public RestaurantProjectionJpaAdapter(RestaurantProjectionJpaRepository restaurantJpaRepository) {
        this.restaurantJpaRepository = restaurantJpaRepository;
    }

    @Override
    @Transactional
    public RestaurantProjection save(RestaurantProjection restaurant) {
        RestaurantProjectionJpaEntity savedEntity =
                restaurantJpaRepository.save(RestaurantProjectionMapper.toEntity(restaurant));

        return RestaurantProjectionMapper.toDomain(savedEntity);
    }


    @Override
    public RestaurantProjection loadById(RestaurantId restaurantId) {
        return restaurantJpaRepository.findById(restaurantId.restaurantId())
                .map(entity -> new RestaurantProjection(
                        RestaurantId.of(entity.getId()),
                        entity.getName(),
                        EmailAddress.of(entity.getEmail()),
                        StreetAddress.of(entity.getStreet(),
                              entity.getNumber(),
                              entity.getPostalCode(),
                              entity.getCity(),
                                entity.getCountry()),
                        CuisineTypeProjection.valueOf(entity.getCuisineType()),
                        entity.getDefaultTimePreparation(),
                        Picture.of(entity.getPicture()),
                        PriceRangeProjection.valueOf(entity.getPriceRange())
                ))
                .orElseThrow();
    }

    @Override
    public Optional<RestaurantProjection> loadRestaurantByName(String name) {
        return restaurantJpaRepository.findByName(name)
                .map(entity -> RestaurantProjection.rehydrate(
                        RestaurantId.of(entity.getId()),
                        entity.getName(),
                        new EmailAddress(entity.getEmail()),
                        new StreetAddress(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        CuisineTypeProjection.valueOf(entity.getCuisineType()),
                        entity.getDefaultTimePreparation(),
                        new Picture(entity.getPicture()),
                        PriceRangeProjection.valueOf(entity.getPriceRange())
                ));
    }

    @Override
    public List<RestaurantProjection> loadAllByType(CuisineTypeProjection type) {
        return restaurantJpaRepository.findByCuisineType(type)
                .stream()
                .map(entity -> RestaurantProjection.rehydrate(
                        RestaurantId.of(entity.getId()),
                        entity.getName(),
                        new EmailAddress(entity.getEmail()),
                        new StreetAddress(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        CuisineTypeProjection.valueOf(entity.getCuisineType()),
                        entity.getDefaultTimePreparation(),
                        new Picture(entity.getPicture()),
                        PriceRangeProjection.valueOf(entity.getPriceRange())
                )).toList();
    }

    @Override
    public List<RestaurantProjection> loadAllByPriceRange(String priceRange) {
        return restaurantJpaRepository.findRestaurantProjectionJpaEntitiesByPriceRange(PriceRangeProjection.valueOf(priceRange))
                .stream()
                .map(entity -> RestaurantProjection.rehydrate(
                        RestaurantId.of(entity.getId()),
                        entity.getName(),
                        new EmailAddress(entity.getEmail()),
                        new StreetAddress(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        CuisineTypeProjection.valueOf(entity.getCuisineType()),
                        entity.getDefaultTimePreparation(),
                        new Picture(entity.getPicture()),
                        PriceRangeProjection.valueOf(entity.getPriceRange())
                ))
                .toList();
    }


    @Override
    public Optional<RestaurantProjection> loadRestaurantById(RestaurantId restaurantId) {
        return restaurantJpaRepository.findRestaurantProjectionJpaEntitiesById(restaurantId.restaurantId())
                .map(entity -> RestaurantProjection.rehydrate(
                        RestaurantId.of(entity.getId()),
                        entity.getName(),
                        new EmailAddress(entity.getEmail()),
                        new StreetAddress(entity.getStreet(), entity.getNumber(),
                                entity.getPostalCode(), entity.getCity(), entity.getCountry()),
                        CuisineTypeProjection.valueOf(entity.getCuisineType()),
                        entity.getDefaultTimePreparation(),
                        new Picture(entity.getPicture()),
                        PriceRangeProjection.valueOf(entity.getPriceRange())
                ));

    }

    @Override
    public List<RestaurantProjection> loadAll() {
        return restaurantJpaRepository.findAll()
                .stream()
                .map(RestaurantProjectionMapper::toDomain)
                .toList();
    }
}
