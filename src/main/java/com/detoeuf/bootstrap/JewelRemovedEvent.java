package com.detoeuf.bootstrap;

public class JewelRemovedEvent implements Event {
    private final Jewel jewel;
    private final AggregateId aggregateId;

    public JewelRemovedEvent(AggregateId aggregateId, Jewel jewel) {
        this.aggregateId = aggregateId;
        this.jewel = jewel;
    }

    public Jewel getJewel() {
        return jewel;
    }

    @Override
    public AggregateId getAggregateId() {
        return this.aggregateId;
    }
}
