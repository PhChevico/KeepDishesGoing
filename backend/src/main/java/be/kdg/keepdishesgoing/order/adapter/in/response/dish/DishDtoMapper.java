package be.kdg.keepdishesgoing.order.adapter.in.response.dish;

import be.kdg.keepdishesgoing.order.domain.DishProjection;

public final class DishDtoMapper {
    
    private DishDtoMapper() {}
    
    public static DishDto toDto(DishProjection dishProjection) {
        return new DishDto(
                dishProjection.getId().dishId(), 
                dishProjection.getName(),
                dishProjection.getTypeOfDish(),
                dishProjection.getFoodTagList(),
                dishProjection.getDescription(), 
                dishProjection.getPrice(),
                dishProjection.getPicture(),
                dishProjection.getDishStatus()
        );
    }
    
}
