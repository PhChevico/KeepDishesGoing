package be.kdg.keepdishesgoing.order.adapter.in.response.order;

import be.kdg.keepdishesgoing.order.domain.BasketItem;
import be.kdg.keepdishesgoing.order.domain.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoMapper {

    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getOrderId().toString(),
                order.getCustomer().getCustomerName().toString(),
                order.getCustomer().getEmailAddress().toString(),
                order.getCustomer().getDeliveryAddress().toString(),
                order.getBasket().getRestaurantId().toString(),
                mapItems(order.getBasket().getItems()),
                order.getBasket().getTotalPrice()
        );
    }

    private static List<OrderItemDto> mapItems(List<BasketItem> items) {
        return items.stream()
                .map(item -> new OrderItemDto(
                        item.getDishId().toString(),
                        item.getPriceAtAddition(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}
