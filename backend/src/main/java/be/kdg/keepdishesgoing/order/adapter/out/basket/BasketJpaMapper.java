package be.kdg.keepdishesgoing.order.adapter.out.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.BasketItem;
import be.kdg.keepdishesgoing.order.domain.vo.AnonymousId;
import be.kdg.keepdishesgoing.order.domain.vo.BasketId;
import be.kdg.keepdishesgoing.order.domain.vo.OrderId;
import be.kdg.keepdishesgoing.order.domain.vo.RestaurantId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketJpaMapper {

    // Domain -> JPA (new entity)
    public static BasketJpaEntity toJpaEntity(Basket basket) {
        BasketJpaEntity entity = new BasketJpaEntity();
        entity.setId(basket.getBasketId().basketId());
        entity.setRestaurantId(basket.getRestaurantId() != null ? basket.getRestaurantId().restaurantId() : null);
        entity.setAnonymousId(basket.getAnonymousId().anonymousId());
        entity.setFirstName(basket.getFirstName());
        entity.setLastName(basket.getLastName());
        entity.setStreet(basket.getStreet());
        entity.setNumber(basket.getNumber());
        entity.setPostalCode(basket.getPostalCode());
        entity.setCity(basket.getCity());
        entity.setCountry(basket.getCountry());
        entity.setEmailAddress(basket.getEmailAddress());
        entity.setPaymentSessionId(basket.getPaymentSessionId());
        entity.setBasketStatus(basket.getStatus());
        entity.setTotalPrice(basket.getTotalPrice());
        entity.setMessage(basket.getMessage());
        if (basket.getOrderId() != null) {
            entity.setOrderId(basket.getOrderId().orderId());
        }

        // Use mutable list to respect orphanRemoval
        entity.setItems(new ArrayList<>());
        for (BasketItem item : basket.getItems()) {
            BasketItemJpaEntity jpaItem = BasketItemJpaMapper.toJpaEntity(item);
            jpaItem.setBasket(entity);
            entity.getItems().add(jpaItem);
        }

        return entity;
    }

    // JPA -> Domain
    public static Basket toDomain(BasketJpaEntity entity) {
        List<BasketItem> items = entity.getItems().stream()
                .map(BasketItemJpaMapper::toDomain)
                .collect(Collectors.toList());

        Basket basket = Basket.rehydrate(
                BasketId.of(entity.getId()),
                entity.getRestaurantId() != null ? RestaurantId.of(entity.getRestaurantId()) : null,
                items,
                entity.getTotalPrice(),
                entity.getBasketStatus(),
                entity.getMessage(),
                AnonymousId.of(entity.getAnonymousId()),
                entity.getPaymentSessionId()
        );

        basket.setAddress(entity.getStreet(), entity.getNumber(), entity.getPostalCode(),
                entity.getCity(), entity.getCountry());
        basket.setEmailAddress(entity.getEmailAddress());
        basket.setFirstName(entity.getFirstName());
        basket.setLastName(entity.getLastName());

        if (entity.getOrderId() != null) {
            basket.setOrderId(OrderId.of(entity.getOrderId()));
        }

        return basket;
    }

    // Update existing JPA entity from domain (DDD-friendly)
    public static void updateFromDomain(BasketJpaEntity entity, Basket basket) {
        entity.setFirstName(basket.getFirstName());
        entity.setLastName(basket.getLastName());
        entity.setStreet(basket.getStreet());
        entity.setNumber(basket.getNumber());
        entity.setPostalCode(basket.getPostalCode());
        entity.setCity(basket.getCity());
        entity.setCountry(basket.getCountry());
        entity.setEmailAddress(basket.getEmailAddress());
        entity.setPaymentSessionId(basket.getPaymentSessionId());
        entity.setBasketStatus(basket.getStatus());
        entity.setTotalPrice(basket.getTotalPrice());
        entity.setMessage(basket.getMessage());
        if (basket.getOrderId() != null) {
            entity.setOrderId(basket.getOrderId().orderId());
        }

        // Proper orphanRemoval: clear and repopulate mutable list
        entity.getItems().clear();
        for (BasketItem item : basket.getItems()) {
            BasketItemJpaEntity jpaItem = BasketItemJpaMapper.toJpaEntity(item);
            jpaItem.setBasket(entity);
            entity.getItems().add(jpaItem);
        }
    }
}
