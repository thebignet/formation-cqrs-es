package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CartSubmittedEvent implements Event {
    private final AggregateId aggregateId;

    @JsonCreator
    public CartSubmittedEvent(@JsonProperty("aggregateId") AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartSubmittedEvent that = (CartSubmittedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId);
    }

    @Override
    public String toString() {
        return "CartSubmittedEvent{" +
                "aggregateId=" + aggregateId +
                '}';
    }
}
