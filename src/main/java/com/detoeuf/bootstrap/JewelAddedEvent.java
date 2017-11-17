package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class JewelAddedEvent implements Event {

    private final AggregateId aggregateId;
    private final Jewel jewel;
    private final SequenceNumber sequenceNumber;

    @JsonCreator
    public JewelAddedEvent(@JsonProperty("aggregateId") AggregateId aggregateId, @JsonProperty("jewel") Jewel jewel, @JsonProperty("sequenceNumber") SequenceNumber sequenceNumber) {
        this.jewel = jewel;
        this.aggregateId = aggregateId;
        this.sequenceNumber = sequenceNumber;
    }

    public Jewel getJewel() {
        return this.jewel;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
    }

    @Override
    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JewelAddedEvent that = (JewelAddedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) &&
                Objects.equals(jewel, that.jewel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, jewel);
    }

    @Override
    public String toString() {
        return "JewelAddedEvent{" +
                "aggregateId=" + aggregateId +
                ", jewel=" + jewel +
                '}';
    }
}
