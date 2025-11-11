package be.kdg.keepdishesgoing.restaurant.port.out.coordinates;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.common.events.order.Coordinates;

public interface GeocodingServicePort {
    Coordinates geocode(Address address);
}
