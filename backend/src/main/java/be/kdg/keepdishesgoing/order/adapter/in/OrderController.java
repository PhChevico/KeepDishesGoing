package be.kdg.keepdishesgoing.order.adapter.in;

import be.kdg.keepdishesgoing.order.adapter.in.response.order.OrderDto;
import be.kdg.keepdishesgoing.order.adapter.in.response.order.OrderDtoMapper;
import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.Order;
import be.kdg.keepdishesgoing.order.port.in.basket.PublishOrderCommand;
import be.kdg.keepdishesgoing.order.port.in.basket.PublishOrderUseCase;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final PublishOrderUseCase publishOrderUseCase;
    private final LoadBasketPort  loadBasketPort;

    public OrderController(PublishOrderUseCase publishOrderUseCase,  LoadBasketPort loadBasketPort) {
        this.publishOrderUseCase = publishOrderUseCase;
        this.loadBasketPort = loadBasketPort;
    }

    @PostMapping("/{orderId}/confirmOrder")
    public ResponseEntity<OrderDto> confirmOrder(
            @PathVariable UUID orderId
    ){
        Basket basket = loadBasketPort.findBasketById(orderId);
        
        log.info("Confirming order: {}", basket);
        
        PublishOrderCommand publishOrderCommand = new PublishOrderCommand(
                basket.getBasketId().basketId(),
                basket.getFirstName(), 
                basket.getLastName(),
                basket.getStreet(),
                basket.getNumber(),
                basket.getPostalCode(),
                basket.getCity(),
                basket.getCountry(),
                basket.getEmailAddress()
        );

        Order order = publishOrderUseCase.publishOrder(publishOrderCommand);

        return ResponseEntity.ok(OrderDtoMapper.toDto(order));
    }

}
