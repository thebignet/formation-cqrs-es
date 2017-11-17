package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class InMemoryEventStore implements EventStore {

    private List<Event> events = List.empty();

    @Override
    public void appendAll(List<Event> events) {
        this.events = this.events.appendAll(events);
    }

    @Override
    public List<Event> getEventsOfAggregate(AggregateId aggregateId) {
        return events;
    }
}
