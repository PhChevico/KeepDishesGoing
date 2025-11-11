package be.kdg.keepdishesgoing.restaurant.scheduler;

import be.kdg.keepdishesgoing.restaurant.core.AutoDeclineOrdersUseCaseImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoDeclineOrdersScheduler {

    private final AutoDeclineOrdersUseCaseImpl autoDeclineOrdersUseCase;

    public AutoDeclineOrdersScheduler(AutoDeclineOrdersUseCaseImpl autoDeclineOrdersUseCase) {
        this.autoDeclineOrdersUseCase = autoDeclineOrdersUseCase;
    }

    // run every minute
    @Scheduled(fixedRateString = "${orders.autodecline.check-interval-ms:60000}")
    public void run() {
        autoDeclineOrdersUseCase.declineStaleOrders();
    }
}

