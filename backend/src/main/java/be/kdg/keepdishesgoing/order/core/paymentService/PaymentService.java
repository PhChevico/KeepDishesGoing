package be.kdg.keepdishesgoing.order.core.paymentService;

import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import be.kdg.keepdishesgoing.order.port.out.payment.PaymentPort;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.stripe.model.checkout.Session;


import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentPort paymentPort;
    private final LoadBasketPort loadBasketPort;
    private final UpdateBasketPort updateBasketPort;

    public String createPayment(BigDecimal amount, UUID basketId, UUID anonymousId) {
        String successUrl = String.format(
                "http://localhost:5173/payment-success?basketId=%s&anonymousId=%s",
                basketId, anonymousId
        );

        String cancelUrl = "https://cancel.url"; // can be static

        Basket basket = loadBasketPort.findBasketById(basketId);

        Session session = paymentPort.createPayment(amount, "EUR", successUrl, cancelUrl);

        basket.assignPaymentSession(session.getId());
        updateBasketPort.saveBasket(basket);

        return session.getUrl();
    }

    public boolean verifyPayment(String sessionId) {
        return paymentPort.verifyPayment(sessionId);
    }
}
