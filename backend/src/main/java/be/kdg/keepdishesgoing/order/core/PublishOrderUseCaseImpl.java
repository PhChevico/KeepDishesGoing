package be.kdg.keepdishesgoing.order.core;

import be.kdg.keepdishesgoing.common.events.order.DeliveryJobLocationUpdatedEvent;
import be.kdg.keepdishesgoing.order.domain.Basket;
import be.kdg.keepdishesgoing.order.domain.Customer;
import be.kdg.keepdishesgoing.order.domain.Order;
import be.kdg.keepdishesgoing.order.domain.vo.CustomerName;
import be.kdg.keepdishesgoing.order.domain.vo.DeliveryAddress;
import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;
import be.kdg.keepdishesgoing.order.port.in.basket.PublishOrderCommand;
import be.kdg.keepdishesgoing.order.port.in.basket.PublishOrderUseCase;
import be.kdg.keepdishesgoing.order.port.in.order.*;
import be.kdg.keepdishesgoing.order.port.out.basket.LoadBasketPort;
import be.kdg.keepdishesgoing.order.port.out.basket.UpdateBasketPort;
import be.kdg.keepdishesgoing.order.port.out.order.OrderEventPublishPort;
import be.kdg.keepdishesgoing.order.port.out.payment.PaymentPort;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class PublishOrderUseCaseImpl implements PublishOrderUseCase, OrderUpdateEventPort {

    private final OrderEventPublishPort orderEventPublishPort;
    private final LoadBasketPort loadBasketPort;
    private final UpdateBasketPort updateBasketPort;
    private final PaymentPort paymentPort;

    public PublishOrderUseCaseImpl(OrderEventPublishPort orderEventPublishPort,
                                   LoadBasketPort loadBasketPort,
                                   UpdateBasketPort updateBasketPort,
                                   PaymentPort paymentPort
    ) {
        this.orderEventPublishPort = orderEventPublishPort;
        this.loadBasketPort = loadBasketPort;
        this.updateBasketPort = updateBasketPort;
        this.paymentPort = paymentPort;
    }

    @Override
    public Order publishOrder(PublishOrderCommand publishOrderCommand) {
        Basket basket = loadBasketPort.findBasketById(publishOrderCommand.basketId());
        
        log.info(basket.getNumber().toString());
        
        String sessionId = basket.getPaymentSessionId();
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalStateException("Missing payment session id. Order cannot be completed.");
        }
        boolean paid = paymentPort.verifyPayment(sessionId);
        if (!paid) {
            throw new IllegalStateException("Payment not verified. Order cannot be completed.");
        }

        Order order = Order.createNew(
                basket,
                new Customer(CustomerName.of(publishOrderCommand.firstName(),
                        publishOrderCommand.lastName()),
                        DeliveryAddress.of(publishOrderCommand.street(),
                                publishOrderCommand.number(),
                                publishOrderCommand.postalCode(),
                                publishOrderCommand.city(),
                                publishOrderCommand.country()),
                        EmailAddress.of(publishOrderCommand.emailAddress()))
        );
        
        orderEventPublishPort.publishOrderEvent(order);
        
        basket.markAsOrdered();
        basket.setOrderId(order.getOrderId());
        updateBasketPort.saveBasket(basket);
        
        return order;
    }

    @Override
    public void project(OrderAcceptedEventCommand orderAcceptedEventCommand) {
        Basket basket = loadBasketPort.findBasketByOrderId(orderAcceptedEventCommand.orderId());
        basket.markAsAccepted();
        
        updateBasketPort.saveBasket(basket);
        
    }

    @Override
    public void project(OrderRefusedEventCommand orderRefusedEventCommand) {
        Basket basket = loadBasketPort.findBasketByOrderId(orderRefusedEventCommand.orderId());
        basket.markAsRefused();
        basket.addMessage(orderRefusedEventCommand.message());
        
        updateBasketPort.saveBasket(basket);
    }

    @Override
    public void project(OrderReadyForPickupEventCommand orderReadyForPickupEventCommand) {
        Basket basket = loadBasketPort.findBasketByOrderId(orderReadyForPickupEventCommand.orderId());
        basket.markAsReadyForPickup();
        updateBasketPort.saveBasket(basket);
    }

    @Override
    public void project(OrderPickedUpEventCommand orderPickedUpEventCommand) {
        Basket basket = loadBasketPort.findBasketByOrderId(orderPickedUpEventCommand.orderId());
        basket.markAsPickedUp();
        updateBasketPort.saveBasket(basket);
    }

    @Override
    public void project(OrderDeliveredEventCommand orderDeliveredEventCommand) {
        Basket basket = loadBasketPort.findBasketByOrderId(orderDeliveredEventCommand.orderId());
        basket.markAsDelivered();
        updateBasketPort.saveBasket(basket);
    }
    
//    public void project(DeliveryJobLocationUpdatedEvent event) {
//        project(new OrderPickedUpEventCommand(
//                event.restaurantId(),
//                event.orderId()
//        ));
//    }

    @Override
    @Transactional
    public void project(DeliveryJobLocationUpdatedCommand command) {
        Basket basket = loadBasketPort.findBasketById(command.orderId());

        if (basket == null) {
            log.warn("Basket with id {} not found, ignoring location update", command.orderId());
            return; // exit early
        }

        // Only mark as picked up if basket is ready
        if (basket.isReadyForPickup() && !basket.isPickedUp()) {
            basket.markAsPickedUp();
            log.info("Order {} marked as picked up", command.orderId());
        } else {
            log.debug("Basket {} not ready or already picked up, ignoring", basket.getBasketId());
        }

        updateBasketPort.saveBasket(basket);
    }




}
