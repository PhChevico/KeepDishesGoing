package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.time.LocalTime;

public record OpeningHours(LocalTime open, LocalTime close) {

    public OpeningHours {
        if (open == null || close == null) {
            throw new IllegalArgumentException("Opening and closing times cannot be null");
        }
        if (close.isBefore(open)) {
            throw new IllegalArgumentException("Closing time must be after opening time");
        }
    }

    public boolean isWithin(LocalTime time) {
        return !time.isBefore(open) && !time.isAfter(close);
    }
}
