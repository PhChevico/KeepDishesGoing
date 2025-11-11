package be.kdg.keepdishesgoing.order.adapter.in.response.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.BasketItem;
import be.kdg.keepdishesgoing.order.domain.vo.AnonymousId;
import be.kdg.keepdishesgoing.order.domain.vo.BasketId;
import be.kdg.keepdishesgoing.order.domain.vo.DishId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.List;
import java.util.stream.Collectors;

public class BasketDtoMapper {

    // Domain -> DTO
    public static BasketDto toDto(Basket basket) {
        List<BasketItemDto> items = basket.getItems().stream()
                .map(BasketDtoMapper::toItemDto)
                .collect(Collectors.toList());

        return new BasketDto(
                basket.getBasketId().basketId(),
                basket.getRestaurantId() != null ? basket.getRestaurantId().restaurantId() : null,
                items,
                basket.getTotalPrice(),
                basket.getBasketStatus(),
                basket.getMessage(),
                basket.getAnonymousId().anonymousId(),
                basket.getPaymentSessionId()
        );
    }

    public static BasketItemDto toItemDto(BasketItem item) {
        return new BasketItemDto(
                item.getDishId().dishId(),
                item.getDishName(),
                item.getPriceAtAddition(),
                item.getQuantity()
                );
    }

    // DTO -> Domain
    public static Basket toDomain(BasketDto dto) {
        List<BasketItem> items = dto.items().stream()
                .map(BasketDtoMapper::toDomainItem)
                .collect(Collectors.toList());

        return Basket.rehydrate(
                BasketId.of(dto.basketId()),
                dto.restaurantId() != null ? RestaurantId.of(dto.restaurantId()) : null,
                items,
                dto.totalPrice(),
                dto.basketStatus(),
                dto.message(),
                AnonymousId.of(dto.anonymousId()),
                dto.paymentSessionId()
        );
    }

    private static BasketItem toDomainItem(BasketItemDto dto) {
        return new BasketItem(
                DishId.of(dto.dishId()),
                dto.name(),
                dto.priceAtAddition(),
                dto.quantity()
                );
    }
}
