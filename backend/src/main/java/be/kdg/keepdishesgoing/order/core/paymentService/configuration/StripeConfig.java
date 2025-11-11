package be.kdg.keepdishesgoing.order.core.paymentService.configuration;

import com.stripe.Stripe;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = System.getenv("STRIPE_SECRET_KEY");
        if (secretKey == null || secretKey.isEmpty()) {
            Dotenv dotenv = Dotenv.load();
            secretKey = dotenv.get("STRIPE_SECRET_KEY");
        }

        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("Stripe secret key is not set!");
        }

        Stripe.apiKey = secretKey;
        System.out.println("Stripe API key initialized");
    }
}
