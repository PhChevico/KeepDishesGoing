package be.kdg.keepdishesgoing.restaurant.adapter.out.restaurantOrder;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.restaurant.domain.OrderLine;
import be.kdg.keepdishesgoing.restaurant.domain.OrderStatus;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.domain.vo.DishId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OrderId;
import be.kdg.keepdishesgoing.restaurant.domain.vo.RestaurantId;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestaurantOrderJpaAdapter implements LoadOrderPort, UpdateOrderPort {
    
    
    private final RestaurantOrderJpaRepository restaurantOrderJpaRepository;

    public RestaurantOrderJpaAdapter(RestaurantOrderJpaRepository restaurantOrderJpaRepository) {
        this.restaurantOrderJpaRepository = restaurantOrderJpaRepository;
    }

    @Override
    public Optional<RestaurantOrder> loadOrderById(UUID orderId) {
        return restaurantOrderJpaRepository.findById(orderId)
                .map(RestaurantOrderJpaMapper::toDomain);
    }

    @Override
    public RestaurantOrder updateOrder(RestaurantOrder restaurantOrder) {
        // try to load existing entity to preserve fields like createdAt
        UUID id = restaurantOrder.getOrderId().orderId();
        Optional<RestaurantOrderJpaEntity> existingOpt = restaurantOrderJpaRepository.findById(id);

        RestaurantOrderJpaEntity entityToSave;

        if (existingOpt.isPresent()) {
            RestaurantOrderJpaEntity existing = existingOpt.get();
            // update mutable fields
            existing.setStreet(restaurantOrder.getDeliveryAddress().street());
            existing.setNumber(restaurantOrder.getDeliveryAddress().number());
            existing.setPostalCode(restaurantOrder.getDeliveryAddress().postalCode());
            existing.setCity(restaurantOrder.getDeliveryAddress().city());
            existing.setCountry(restaurantOrder.getDeliveryAddress().country());
            existing.setRestaurantId(restaurantOrder.getRestaurantId().restaurantId());
            existing.setTotalPrice(restaurantOrder.getTotalPrice());
            existing.setStatus(restaurantOrder.getStatus());

            // replace order lines
            List<OrderLineJpaEntity> jpaLines = restaurantOrder.getOrderLines().stream()
                    .map(line -> new OrderLineJpaEntity(
                            line.getDishId().dishId(),
                            line.getPriceAtAddition(),
                            line.getQuantity()
                    ))
                    .toList();

            existing.getOrderLines().clear();
            jpaLines.forEach(l -> l.setRestaurantOrder(existing));
            existing.getOrderLines().addAll(jpaLines);

            entityToSave = existing;
        } else {
            entityToSave = RestaurantOrderJpaMapper.fromDomain(restaurantOrder);
        }

        RestaurantOrderJpaEntity saved = restaurantOrderJpaRepository.save(entityToSave);
        return RestaurantOrderJpaMapper.toDomain(saved);
    }

    @Override
    public List<RestaurantOrder> loadOrdersByRestaurantId(UUID restaurantId) {
        List<RestaurantOrderJpaEntity> restaurantOrderJpaEntities =
                restaurantOrderJpaRepository.findByRestaurantIdWithOrderLines(restaurantId);

        return restaurantOrderJpaEntities.stream()
                .map(this::mapToDomain)
                .toList();

    }

    @Override
    public List<RestaurantOrder> loadPendingOrdersOlderThan(Instant cutoff) {
        long cutoffMillis = cutoff.toEpochMilli();
        List<RestaurantOrderJpaEntity> entities = restaurantOrderJpaRepository.findPendingOrdersOlderThan(cutoffMillis);
        return entities.stream().map(this::mapToDomain).toList();
    }

    private RestaurantOrder mapToDomain(RestaurantOrderJpaEntity entity) {
        List<OrderLine> orderLines = entity.getOrderLines().stream()
                .map(orderLineEntity -> new OrderLine(
                        DishId.of(orderLineEntity.getDishId()),
                        orderLineEntity.getPriceAtAddition(),
                        orderLineEntity.getQuantity()
                ))
                .toList();

        Address deliveryAddress = Address.of(
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getCountry()
        );

        RestaurantOrder order = new RestaurantOrder(
                OrderId.of(entity.getOrderId()),
                deliveryAddress,
                RestaurantId.of(entity.getRestaurantId()),
                entity.getTotalPrice(),
                orderLines
        );

        order.setStatus(OrderStatus.valueOf(entity.getStatus().name()));

        return order;
    }
}
