package be.kdg.keepdishesgoing.common.events.order;

public record Coordinates(
        double lat,
        double lng
) {
    
    public static Coordinates of(double lat, double lng) {
        return new Coordinates(lat, lng);
    }
    
}
