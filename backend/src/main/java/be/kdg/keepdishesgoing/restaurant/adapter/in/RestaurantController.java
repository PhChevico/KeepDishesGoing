package be.kdg.keepdishesgoing.restaurant.adapter.in;

import be.kdg.keepdishesgoing.restaurant.adapter.in.request.CreateRestaurantRequest;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.OrderDto;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.RestaurantDto;
import be.kdg.keepdishesgoing.restaurant.adapter.in.response.RestaurantDtoMapper;
import be.kdg.keepdishesgoing.restaurant.domain.Restaurant;
import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.domain.vo.OwnerId;
import be.kdg.keepdishesgoing.restaurant.port.in.order.*;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.CreateRestaurantCommand;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.keepdishesgoing.restaurant.port.in.restaurant.FindRestaurantPort;
import be.kdg.keepdishesgoing.restaurant.port.out.restaurant.LoadRestaurantPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/ownerRestaurants")
public class RestaurantController {
    
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final OrderAcceptUseCase orderAcceptUseCase;
    private final OrderRefuseUseCase orderRefuseUseCase;
    private final OrderReadyForPickupUseCase orderReadyForPickupUseCase;
    private final FindRestaurantPort findRestaurantPort;
    private final FindRestaurantOrderUseCase findRestaurantOrderUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, OrderAcceptUseCase orderAcceptUseCase,
                                OrderRefuseUseCase orderRefuseUseCase,
                                OrderReadyForPickupUseCase orderReadyForPickupUseCase,
                                FindRestaurantOrderUseCase findRestaurantOrderUseCase,
                                FindRestaurantPort findRestaurantPort
                                ) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.orderAcceptUseCase = orderAcceptUseCase;
        this.orderRefuseUseCase = orderRefuseUseCase;
        this.orderReadyForPickupUseCase = orderReadyForPickupUseCase;
        this.findRestaurantOrderUseCase = findRestaurantOrderUseCase;
        this.findRestaurantPort = findRestaurantPort;
    }
    
    @GetMapping
    public ResponseEntity<RestaurantDto> getRestaurant(@AuthenticationPrincipal Jwt principal){
        UUID ownerId = UUID.fromString(principal.getSubject());
        log.info(ownerId.toString());
        Restaurant restaurant = findRestaurantPort.findRestaurantByOwnerId(OwnerId.of(ownerId));

        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(RestaurantDtoMapper.toDto(restaurant));
    }


    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest,
            @AuthenticationPrincipal Jwt principal
    ) {
        UUID ownerId = UUID.fromString(principal.getSubject());
        log.info("Creating restaurant for owner: {}", ownerId);

        CreateRestaurantCommand createRestaurantCommand = new CreateRestaurantCommand(
                ownerId, 
                createRestaurantRequest.name(),
                createRestaurantRequest.email(),
                createRestaurantRequest.street(),
                createRestaurantRequest.number(),
                createRestaurantRequest.postalCode(),
                createRestaurantRequest.city(),
                createRestaurantRequest.country(),
                createRestaurantRequest.cuisineType(),
                createRestaurantRequest.defaultTimePreparation(),
                createRestaurantRequest.picture(),
                createRestaurantRequest.priceRange()
        );

        Restaurant restaurant = createRestaurantUseCase.createRestaurant(createRestaurantCommand);
        return ResponseEntity.ok(RestaurantDtoMapper.toDto(restaurant));
    }
    
    
    @PostMapping("orders/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(
            @PathVariable UUID orderId
    ) {
        OrderAcceptCommand command = new OrderAcceptCommand(orderId);
        orderAcceptUseCase.acceptOrder(command);
        return ResponseEntity.ok().build();
    }
    
    
    @PostMapping("orders/{orderId}/refuse")
    public ResponseEntity<Void> refuseOrder(
            @PathVariable UUID orderId,
            @RequestBody String message
    ) {
        OrderRefuseCommand command = new OrderRefuseCommand(orderId, message);
        orderRefuseUseCase.refuseOrder(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/ready-for-pickup")
    public ResponseEntity<Void> markOrderReadyForPickup(@PathVariable("orderId") UUID orderId) {
        OrderReadyForPickupCommand command = new OrderReadyForPickupCommand(orderId);
        orderReadyForPickupUseCase.orderReadyForPickup(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    @GetMapping("/{restaurantId}/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable UUID restaurantId) {
        List<RestaurantOrder> orders = findRestaurantOrderUseCase.findRestaurantOrders(restaurantId);

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(
                        order.getOrderId().orderId(),
                        order.getDeliveryAddress(),
                        order.getRestaurantId().restaurantId(),
                        order.getTotalPrice(),
                        order.getOrderLines(),
                        order.getStatus().name()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderDtos);
    }
    

}
