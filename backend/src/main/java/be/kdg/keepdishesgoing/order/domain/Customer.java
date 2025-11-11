package be.kdg.keepdishesgoing.order.domain;

import be.kdg.keepdishesgoing.order.domain.vo.CustomerName;
import be.kdg.keepdishesgoing.order.domain.vo.DeliveryAddress;
import be.kdg.keepdishesgoing.order.domain.vo.EmailAddress;

public class Customer {

    private EmailAddress emailAddress;
    private CustomerName customerName;
    private DeliveryAddress deliveryAddress;

    public Customer(CustomerName customerName, DeliveryAddress deliveryAddress, EmailAddress emailAddress) {
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.emailAddress = emailAddress;
    }

    public CustomerName getCustomerName() {
        return customerName;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }
}
