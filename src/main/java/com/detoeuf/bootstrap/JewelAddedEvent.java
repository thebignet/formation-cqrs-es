package com.detoeuf.bootstrap;

import java.util.Objects;

public class JewelAddedEvent implements Event {

    private final AggregateId aggregateId;
    private final Jewel jewel;

    public JewelAddedEvent(AggregateId aggregateId, Jewel jewel) {
        this.jewel = jewel;
        this.aggregateId = aggregateId;
    }

    public Jewel getJewel() {
        return this.jewel;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
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
}
