package be.kdg.keepdishesgoing.order.adapter.out.order;


import be.kdg.keepdishesgoing.order.domain.Order;
import be.kdg.keepdishesgoing.order.port.out.order.OrderEventPublishPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements OrderEventPublishPort {

    private final ApplicationEventPublisher applicationEventPublisher;


    public OrderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void publishOrderEvent(Order order) {
        order.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        order.clearDomainEvents();
    }
}
