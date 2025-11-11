package be.kdg.keepdishesgoing.order.adapter.out.basket;

import be.kdg.keepdishesgoing.order.domain.BasketItem;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;

import java.util.List;
import java.util.stream.Collectors;

public class BasketItemJpaMapper {

    // Domain -> JPA
    public static BasketItemJpaEntity toJpaEntity(BasketItem item) {
        BasketItemJpaEntity entity = new BasketItemJpaEntity();
        entity.setDishId(item.getDishId().dishId());
        entity.setDishName(item.getDishName());        // <-- add this
        entity.setPriceAtAddition(item.getPriceAtAddition());
        entity.setQuantity(item.getQuantity());
        return entity;
    }


    public static List<BasketItemJpaEntity> toJpaEntityList(List<BasketItem> items) {
        return items.stream()
                .map(BasketItemJpaMapper::toJpaEntity)
                .collect(Collectors.toList());
    }

    // JPA -> Domain
    public static BasketItem toDomain(BasketItemJpaEntity entity) {
        return new BasketItem(
                new DishId(entity.getDishId()),
                entity.getDishName(),
                entity.getPriceAtAddition(),
                entity.getQuantity()
        );
    }

    public static List<BasketItem> toDomainList(List<BasketItemJpaEntity> entities) {
        return entities.stream()
                .map(BasketItemJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
