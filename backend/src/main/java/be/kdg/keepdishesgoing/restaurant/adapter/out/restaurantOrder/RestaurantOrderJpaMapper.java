package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.restaurant.domain.OrderLine;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OrderId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;

import java.util.List;

public class RestaurantOrderJpaMapper {

    public static RestaurantOrder toDomain(RestaurantOrderJpaEntity jpaEntity) {

        List<OrderLine> orderLines = jpaEntity.getOrderLines().stream()
                .map(line -> new OrderLine(
                        DishId.of(line.getDishId()),
                        line.getPriceAtAddition(),
                        line.getQuantity()
                ))
                .toList();

        RestaurantOrder restaurantOrder = new RestaurantOrder(
                OrderId.of(jpaEntity.getOrderId()),
                Address.of(jpaEntity.getStreet(),
                        jpaEntity.getNumber(),
                        jpaEntity.getPostalCode(),
                        jpaEntity.getCity(),
                        jpaEntity.getCountry()),
                RestaurantId.of(jpaEntity.getRestaurantId()),
                jpaEntity.getTotalPrice(),
                orderLines
                );
        
        restaurantOrder.setStatus(jpaEntity.getStatus());
        
        return restaurantOrder;
        
    }

    public static RestaurantOrderJpaEntity fromDomain(RestaurantOrder domain) {
        List<OrderLineJpaEntity> jpaOrderLines = domain.getOrderLines().stream()
                .map(line -> new OrderLineJpaEntity(
                        line.getDishId().dishId(),           // unwrap DishId
                        line.getPriceAtAddition(),
                        line.getQuantity()
                ))
                .toList();

        return new RestaurantOrderJpaEntity(
                domain.getOrderId().orderId(),               // unwrap OrderId
                domain.getDeliveryAddress().street(),
                domain.getDeliveryAddress().number(),
                domain.getDeliveryAddress().postalCode(),
                domain.getDeliveryAddress().city(),
                domain.getDeliveryAddress().country(),
                domain.getRestaurantId().restaurantId(),     // unwrap RestaurantId
                domain.getTotalPrice(),
                domain.getStatus(),
                jpaOrderLines
        );
    }
}
