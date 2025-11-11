package be.kdg.keepdishesgoing.restaurant.domain.vo;

import java.util.UUID;

public record OwnerId (UUID ownerId) {

    public static OwnerId of(UUID uuid) {
        return new OwnerId(uuid);
    }

    public static OwnerId create() {
        return new OwnerId(UUID.randomUUID());
    }

}
