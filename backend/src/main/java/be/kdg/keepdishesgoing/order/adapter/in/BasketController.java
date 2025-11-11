package be.kdg.keepdishesgoing.order.adapter.in;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.order.adapter.in.request.AddDishRequest;
import be.kdg.keepdishesgoing.order.adapter.in.request.CheckoutBasketRequest;
import be.kdg.keepdishesgoing.order.adapter.in.request.CreateBasketRequest;
import be.kdg.keepdishesgoing.order.adapter.in.request.RemoveDishRequest;
import be.kdg.keepdishesgoing.order.adapter.in.response.basket.*;
import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.in.basket.*;
import be.kdg.keepdishesgoing.order.port.in.dish.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private static final Logger log = LoggerFactory.getLogger(BasketController.class);
    private final FindBasketUseCase  findBasketUseCase;
    private final AddDishToBasketUseCase addDishToBasketUseCase;
    private final RemoveDishFromBasketUseCase  removeDishFromBasketUseCase;
    private final CreateBasketUseCase createBasketUseCase;
    private final CheckoutBasketUseCase checkoutBasketUseCase;

    public BasketController(FindBasketUseCase  findBasketUseCase,  AddDishToBasketUseCase addBasketUseCase,
        RemoveDishFromBasketUseCase removeDishFromBasketUseCase,
                            CreateBasketUseCase createBasketUseCase,
                            CheckoutBasketUseCase checkoutBasketUseCase
    ) {
        this.findBasketUseCase = findBasketUseCase;
        this.addDishToBasketUseCase = addBasketUseCase;
        this.removeDishFromBasketUseCase = removeDishFromBasketUseCase;
        this.createBasketUseCase = createBasketUseCase;
        this.checkoutBasketUseCase = checkoutBasketUseCase;
    }


    @GetMapping("/{id}")
    public ResponseEntity<BasketDto> getBasket(@PathVariable UUID id) {
        Basket basket = findBasketUseCase.findBasketById(id);
        
        //map to dto
        return ResponseEntity.ok(BasketDtoMapper.toDto(basket));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable UUID id) {
        Basket basket = findBasketUseCase.findBasketById(id);
        OrderDetailsDto orderDetailsDto = OrderDetailsMapper.toDto(basket);
        return ResponseEntity.ok(orderDetailsDto);
    }
    
    @PostMapping
    public ResponseEntity<BasketDto> createBasket(@RequestBody CreateBasketRequest createBasketRequest) {
        CreateBasketCommand createBasketCommand = new CreateBasketCommand(
                createBasketRequest.restaurantId(),
                createBasketRequest.anonymousId()
        );
        
        Basket savedBasket = createBasketUseCase.createBasket(createBasketCommand);
        BasketDto basketDto = BasketDtoMapper.toDto(savedBasket);
     
        return ResponseEntity.ok(basketDto);
    }

    @PostMapping("/{basketId}/checkout")
    public ResponseEntity<BasketDto> checkoutBasket(
            @PathVariable UUID basketId,
            @RequestBody CheckoutBasketRequest request
    ) {
        CheckoutBasketCommand checkoutBasketCommand = new CheckoutBasketCommand(
                basketId,
                request.firstName(),
                request.lastName(),
                request.street(),
                request.number(),
                request.postalCode(),
                request.city(),
                request.country(),
                request.emailAddress()
        );
        Basket updatedBasket = checkoutBasketUseCase.checkoutBasket(checkoutBasketCommand);
        return ResponseEntity.ok(BasketDtoMapper.toDto(updatedBasket));
    }


    @PostMapping("/{id}/addDish")
    public ResponseEntity<BasketDto> addDishToBasket(
            @PathVariable("id") UUID basketId,
            @RequestBody AddDishRequest request
    ) {
        AddDishToBasketCommand command = new AddDishToBasketCommand(
                basketId,
                request.dishId(),
                request.dishName(),
                request.quantity()
        );

        Basket updatedBasket = addDishToBasketUseCase.addDishToBasket(command);
        
        return ResponseEntity.ok(BasketDtoMapper.toDto(updatedBasket));
    }

    @PatchMapping("/{id}/removeDish")
    public ResponseEntity<BasketDto> removeDishFromBasket(
            @PathVariable("id") UUID basketId,
            @RequestBody RemoveDishRequest request
    ) {
        RemoveDishFromBasketCommand command = new RemoveDishFromBasketCommand(
                request.dishId(),
                basketId,
                request.quantity()
        );

        log.info(command.dishId().toString());
        
        Basket updatedBasket = removeDishFromBasketUseCase.removeDishFromBasket(command);

        return ResponseEntity.ok(BasketDtoMapper.toDto(updatedBasket));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<BasketDto> getBasketByRestaurant(
            @PathVariable UUID restaurantId,
            @RequestHeader("X-Anonymous-ID") UUID anonymousId
    ) {
        Basket basket = findBasketUseCase.findBasketByRestaurantIdAndAnonymousId(
                restaurantId,
                anonymousId
        );

        if (basket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BasketDtoMapper.toDto(basket));
    }
}
