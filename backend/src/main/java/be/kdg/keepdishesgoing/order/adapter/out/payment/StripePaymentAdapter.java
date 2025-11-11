package be.kdg.keepdishesgoing.order.adapter.out.payment;

import be.kdg.keepdishesgoing.order.port.out.payment.PaymentPort;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class StripePaymentAdapter implements PaymentPort {

    @Override
    public Session createPayment(BigDecimal amount, String currency, String successUrl, String cancelUrl) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(cancelUrl)
                    .addAllLineItem(List.of(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // Stripe uses cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Order Payment")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    ))
                    .build();

            Session session = Session.create(params);
            return session; // redirect user here

        } catch (Exception e) {
            throw new RuntimeException("Error creating Stripe session", e);
        }
    }

    @Override
    public boolean verifyPayment(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            // Option 1: check payment status on session
            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
                return true;
            }
            // Option 2: check underlying payment intent status
            String paymentIntentId = session.getPaymentIntent();
            if (paymentIntentId != null) {
                PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
                return "succeeded".equalsIgnoreCase(intent.getStatus());
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error verifying Stripe payment", e);
        }
    }
}
