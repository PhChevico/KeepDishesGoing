package be.kdg.keepdishesgoing.restaurant.adapter.in.response;

import be.kdg.keepdishesgoing.restaurant.adapter.out.dish.DishJpaEntity;
import be.kdg.keepdishesgoing.restaurant.domain.Dish;

public final class DishDtoMapper {
    
    private DishDtoMapper() {}
    
    public static DishDto toDto(Dish dish) {
        return new DishDto(
                dish.getDishId().dishId(),
                dish.getName(),
                dish.getTypeOfDish(),
                dish.getFoodTagList(),
                dish.getDescription(), 
                dish.getPrice(),
                dish.getPicture(),
                dish.getDishStatus()
        );
    }
    
}
