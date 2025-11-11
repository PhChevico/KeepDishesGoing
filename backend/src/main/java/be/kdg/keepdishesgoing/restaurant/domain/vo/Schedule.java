package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

public record Schedule(Map<DayOfWeek, OpeningHours> hoursPerDay) {

    public Schedule {
        if (hoursPerDay == null || hoursPerDay.isEmpty()) {
            throw new IllegalArgumentException("Schedule cannot be null or empty");
        }
    }

    public OpeningHours getFor(DayOfWeek day) {
        return hoursPerDay.get(day);
    }

    public boolean isOpen(DayOfWeek day, LocalTime time) {
        OpeningHours hours = hoursPerDay.get(day);
        return hours != null && hours.isWithin(time);
    }
}
