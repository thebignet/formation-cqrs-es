package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class AggregateId {
    private final String uuid;

    @JsonCreator
    public AggregateId(@JsonProperty("uuid") String uuid) {
        this.uuid = uuid;
    }

    public static AggregateId generate() {
        return new AggregateId(UUID.randomUUID().toString());
    }

    public String serialize() {
        return uuid.toString();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "AggregateId{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateId that = (AggregateId) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
