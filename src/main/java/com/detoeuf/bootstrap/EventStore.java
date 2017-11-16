package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class EventStore {

    private List<Event> events = List.empty();

    public void appendAll(List<Event> events) {
        this.events = this.events.appendAll(events);
    }

    public List<Event> getAll() {
        return events;
    }
}
