package be.kdg.keepdishesgoing.restaurant.core;

import be.kdg.keepdishesgoing.restaurant.domain.RestaurantOrder;
import be.kdg.keepdishesgoing.restaurant.port.in.order.OrderEventPublisherPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.LoadOrderPort;
import be.kdg.keepdishesgoing.restaurant.port.out.order.UpdateOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class AutoDeclineOrdersUseCaseImpl {
    private static final Logger log = LoggerFactory.getLogger(AutoDeclineOrdersUseCaseImpl.class);
    private static final long STALE_MINUTES = 5;

    private final LoadOrderPort loadOrderPort;
    private final UpdateOrderPort updateOrderPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    public AutoDeclineOrdersUseCaseImpl(LoadOrderPort loadOrderPort, UpdateOrderPort updateOrderPort, OrderEventPublisherPort orderEventPublisherPort) {
        this.loadOrderPort = loadOrderPort;
        this.updateOrderPort = updateOrderPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    public void declineStaleOrders() {
        Instant cutoff = Instant.now().minus(STALE_MINUTES, ChronoUnit.MINUTES);
        List<RestaurantOrder> stale = loadOrderPort.loadPendingOrdersOlderThan(cutoff);

        for (RestaurantOrder order : stale) {
            try {
                order.refuse("Automatically declined due to no decision within 5 minutes");
                updateOrderPort.updateOrder(order);
                orderEventPublisherPort.publishDomainEvent(order);
                log.info("Auto-declined order {}", order.getOrderId().orderId());
            } catch (Exception ex) {
                log.error("Failed to auto-decline order {}: {}", order.getOrderId().orderId(), ex.getMessage(), ex);
            }
        }
    }
}

