package be.kdg.keepdishesgoing.restaurant.adapter.out.coordinate;

import be.kdg.keepdishesgoing.common.events.order.Address;
import be.kdg.keepdishesgoing.common.events.order.Coordinates;
import be.kdg.keepdishesgoing.restaurant.port.out.coordinates.GeocodingServicePort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NominatimGeocodingAdaptor implements GeocodingServicePort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Coordinates geocode(Address address) {
        String query = String.format(
                "%s %s, %s %s",
                address.street(), address.number(),
                address.postalCode(), address.city()
        );

        String url = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", query)
                .queryParam("format", "json")
                .queryParam("limit", "1")
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "KeepDishesGoing/1.0");

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<NominatimResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, request, NominatimResponse[].class
        );

        NominatimResponse[] results = response.getBody();
        if (results != null && results.length > 0) {
            return new Coordinates(
                    Double.parseDouble(results[0].lat),
                    Double.parseDouble(results[0].lon)
            );
        }

        throw new RuntimeException("Geocoding failed for address: " + query);
    }

    static class NominatimResponse {
        public String lat;
        public String lon;
    }
}

