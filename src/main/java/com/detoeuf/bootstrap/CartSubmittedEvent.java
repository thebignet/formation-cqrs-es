package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CartSubmittedEvent implements Event {
    private final AggregateId aggregateId;
    private final int sequenceNumber;

    @JsonCreator
    public CartSubmittedEvent(@JsonProperty("aggregateId") AggregateId aggregateId, @JsonProperty("sequenceNumber") int sequenceNumber) {
        this.aggregateId = aggregateId;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
    }

    @Override
    public int getSequenceNumber() {
        return sequenceNumber;
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
