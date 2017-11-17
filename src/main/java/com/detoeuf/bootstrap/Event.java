package com.detoeuf.bootstrap;

public interface Event {
    AggregateId getAggregateId();

    int getSequenceNumber();
}
