package be.kdg.keepdishesgoing.restaurant.adapter.out.dish;

import be.kdg.keepdishesgoing.restaurant.adapter.out.dishFoodTags.DishFoodTagJpaEntity;
import be.kdg.keepdishesgoing.restaurant.adapter.out.foodMenu.FoodMenuJpaEntity;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;
import be.kdg.keepdishesgoing.restaurant.domain.FoodTag;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.FoodMenuId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DishJpaMapper {

    private DishJpaMapper() {}

    public static DishJpaEntity fromDomain(Dish dish) {
        DishJpaEntity entity = new DishJpaEntity();
        entity.setId(dish.getDishId() != null ? dish.getDishId().dishId() : UUID.randomUUID());
        entity.setName(dish.getName());
        entity.setTypeOfDish(dish.getTypeOfDish());

        // Convert FoodTag list to DishFoodTagJpaEntity set
        Set<DishFoodTagJpaEntity> foodTagEntities = new HashSet<>();
        if (dish.getFoodTagList() != null) {
            for (FoodTag tag : dish.getFoodTagList()) {
                DishFoodTagJpaEntity tagEntity = new DishFoodTagJpaEntity();
                tagEntity.setTag(tag);
                tagEntity.setDish(entity); // back-reference!
                foodTagEntities.add(tagEntity);
            }
        }
        entity.setFoodTags(foodTagEntities);

        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPicture(dish.getPicture());
        entity.setDishStatus(dish.getDishStatus());
        entity.setRestaurantId(dish.getRestaurantId().restaurantId());
        if (dish.getFoodMenuId() != null) {
            FoodMenuJpaEntity menuEntity = new FoodMenuJpaEntity();
            menuEntity.setId(dish.getFoodMenuId().foodMenuId());
            entity.setFoodMenu(menuEntity);
        }

        return entity;
    }


    // JPA -> Domain
    public static Dish toDomain(DishJpaEntity entity) {
        Dish dish = Dish.rehydrate(
                DishId.of(entity.getId()),
                entity.getName(),
                entity.getTypeOfDish(),
                null, // food tags will be set below
                entity.getDescription(),
                entity.getPrice(),
                entity.getPicture(),
                entity.getDishStatus(),
                RestaurantId.of(entity.getRestaurantId())
        );

        if (entity.getFoodMenu() != null) {
            dish.setFoodMenuId(FoodMenuId.of(entity.getFoodMenu().getId()));
        }


        // Map DishFoodTagProjectionJpaEntity -> List<FoodTag>
        if (entity.getFoodTags() != null) {
            dish.setFoodTagList(entity.getFoodTags().stream()
                    .map(DishFoodTagJpaEntity::getTag)
                    .toList()
            );
        }

        return dish;
    }
}
