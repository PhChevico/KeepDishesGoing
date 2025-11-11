package be.kdg.keepdishesgoing.order.adapter.out.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;
import be.kdg.keepdishesgoing.order.domain.FoodTagProjection;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.*;

public final class DishProjectionJpaMapper {

    private DishProjectionJpaMapper() {}

    public static DishProjectionJpaEntity fromDomain(DishProjection dish) {
        DishProjectionJpaEntity entity = new DishProjectionJpaEntity();

        entity.setId(dish.getId() != null ? dish.getId().dishId() : UUID.randomUUID());
        entity.setName(dish.getName());
        entity.setTypeOfDish(dish.getTypeOfDish());
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPicture(dish.getPicture());
        entity.setDishStatus(dish.getDishStatus());
        entity.setRestaurantId(dish.getRestaurantId().restaurantId());
        entity.setFoodMenuId(dish.getFoodMenuId() != null ? dish.getFoodMenuId().foodMenuId() : null);

        List<String> foodTags = dish.getFoodTagList().stream()
                .map(Enum::name)
                .toList();
        entity.setFoodTags(foodTags);


        return entity;
    }

    public static DishProjection toDomain(DishProjectionJpaEntity entity) {
        // Map String back to FoodTag enum
        List<FoodTagProjection> foodTagList = new ArrayList<>();
        if (entity.getFoodTags() != null) {
            for (String tagString : entity.getFoodTags()) {
                foodTagList.add(FoodTagProjection.valueOf(tagString)); // Convert string back to enum
            }
        }

        return new DishProjection(
                DishId.of(entity.getId()),
                entity.getName(),
                entity.getTypeOfDish(),
                foodTagList,
                entity.getDescription(),
                entity.getPrice(),
                entity.getPicture(),
                entity.getDishStatus(),
                entity.getFoodMenuId() != null ? FoodMenuId.of(entity.getFoodMenuId()) : null,
                RestaurantId.of(entity.getRestaurantId())
        );
    }
}