package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public interface EventStore {
    void appendAll(List<Event> events);

    List<Event> getEventsOfAggregate(AggregateId aggregateId);
}
