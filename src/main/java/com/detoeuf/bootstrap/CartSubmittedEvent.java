package com.detoeuf.bootstrap;

public class CartSubmittedEvent implements Event {
    private final AggregateId aggregateId;

    public CartSubmittedEvent(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
    }
}
