package be.kdg.keepdishesgoing.order.adapter.in;


import be.kdg.keepdishesgoing.order.core.paymentService.PaymentService;
import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final LoadBasketPort loadBasketPort;

    @PostMapping("/create")
    public String createPayment(@RequestParam UUID basketId) {
        Basket basket = loadBasketPort.findBasketById(basketId);

        if (basket.getTotalPrice() == null || basket.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Basket is empty, cannot pay.");
        }

        return paymentService.createPayment(
                basket.getTotalPrice(), basketId, basket.getAnonymousId().anonymousId()
        );
    }

}
