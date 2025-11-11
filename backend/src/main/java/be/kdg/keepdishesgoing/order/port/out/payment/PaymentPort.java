package be.kdg.keepdishesgoing.order.port.out.payment;

import java.math.BigDecimal;
import com.stripe.model.checkout.Session;


public interface PaymentPort {
    Session createPayment(BigDecimal amount, String currency, String successUrl, String cancelUrl);
    boolean verifyPayment(String sessionId);
}
