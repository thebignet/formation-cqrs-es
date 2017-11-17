package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JewelRemovedEvent implements Event {
    private final Jewel jewel;
    private final AggregateId aggregateId;
    private final SequenceNumber sequenceNumber;

    public JewelRemovedEvent(@JsonProperty("aggregateId") AggregateId aggregateId, @JsonProperty("sequenceNumber") SequenceNumber sequenceNumber, @JsonProperty("jewel") Jewel jewel) {
        this.aggregateId = aggregateId;
        this.jewel = jewel;
        this.sequenceNumber = sequenceNumber;
    }

    public Jewel getJewel() {
        return jewel;
    }

    @Override
    public AggregateId getAggregateId() {
        return this.aggregateId;
    }

    @Override
    public SequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }
}
