package com.detoeuf.bootstrap;

import java.util.UUID;

public class AggregateId {
    private final UUID uuid;

    private AggregateId(UUID uuid) {
        this.uuid = uuid;
    }

    public static AggregateId generate() {
        return new AggregateId(UUID.randomUUID());
    }

    public String serialize() {
        return uuid.toString();
    }

    public UUID getUuid() {
        return uuid;
    }
}
