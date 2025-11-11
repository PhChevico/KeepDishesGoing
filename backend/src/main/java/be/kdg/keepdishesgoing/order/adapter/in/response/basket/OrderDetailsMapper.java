package be.kdg.keepdishesgoing.order.adapter.in.response.basket;

import be.kdg.keepdishesgoing.order.domain.Basket;

import java.util.List;

public final class OrderDetailsMapper {

    private OrderDetailsMapper() {}

    public static OrderDetailsDto toDto(Basket basket) {
        // Reuse item mapping logic from BasketDtoMapper
        List<BasketItemDto> itemDtos = basket.getItems().stream()
                .map(BasketDtoMapper::toItemDto) // Assume this helper method exists
                .toList();

        // Map embedded DTOs
        ClientInfoDto clientInfo = toClientInfoDto(basket);
        AddressDto address = toAddressDto(basket);

        return new OrderDetailsDto(
                basket.getBasketId().basketId(),
                basket.getRestaurantId().restaurantId(),
                itemDtos,
                basket.getTotalPrice(),
                basket.getBasketStatus(),
                basket.getMessage(),
                basket.getAnonymousId() != null ? basket.getAnonymousId().anonymousId() : null,
                basket.getPaymentSessionId(),

                clientInfo,
                address
        );
    }

    private static ClientInfoDto toClientInfoDto(Basket basket) {
        return new ClientInfoDto(
                basket.getFirstName(),
                basket.getLastName(),
                basket.getEmailAddress()
        );
    }

    private static AddressDto toAddressDto(Basket basket) {
        return new AddressDto(
                basket.getStreet(),
                basket.getNumber(),
                basket.getPostalCode(),
                basket.getCity(),
                basket.getCountry()
        );
    }
    
}
