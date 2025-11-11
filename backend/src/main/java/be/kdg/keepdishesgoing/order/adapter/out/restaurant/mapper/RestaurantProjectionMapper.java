package be.kdg.keepdishesgoing.order.adapter.out.restaurant.mapper;

import be.kdg.keepdishesgoing.order.adapter.out.restaurant.RestaurantProjectionJpaEntity;
import be.kdg.keepdishesgoing.order.domain.CuisineTypeProjection;
import be.kdg.keepdishesgoing.order.domain.PriceRangeProjection;
import be.kdg.keepdishesgoing.order.domain.RestaurantProjection;
import be.kdg.keepdishesgoing.order.domain.vo.*;
import org.springframework.stereotype.Component;

@Component
public class RestaurantProjectionMapper {

    public static RestaurantProjectionJpaEntity toEntity(RestaurantProjection restaurant) {
        return new RestaurantProjectionJpaEntity(
                restaurant.getRestaurantId().restaurantId(),
                restaurant.getName(),
                restaurant.getEmail().emailAddress(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().postalCode(),
                restaurant.getAddress().city(),
                restaurant.getAddress().country(),
                restaurant.getCuisineType().toString(),
                restaurant.getDefaultTimePreparation(),
                restaurant.getPicture().url(),
                restaurant.getPriceRange().toString()
        );
    }

    public static RestaurantProjection toDomain(RestaurantProjectionJpaEntity entity) {
        return new RestaurantProjection(
                RestaurantId.of(entity.getId()),
                entity.getName(),
                EmailAddress.of(entity.getEmail()),
                StreetAddress.of(
                        entity.getStreet(),
                        entity.getNumber(),
                        entity.getPostalCode(),
                        entity.getCity(),
                        entity.getCountry()
                ),
                CuisineTypeProjection.valueOf(entity.getCuisineType()),
                entity.getDefaultTimePreparation(),
                Picture.of(entity.getPicture()),
                PriceRangeProjection.valueOf(entity.getPriceRange())
        );
    }
}
