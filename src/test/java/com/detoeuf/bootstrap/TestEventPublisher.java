package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class TestEventPublisher implements EventPublisher {
    private List<Event> events = List.empty();

    public List<Event> listPublishedEvents() {
        return events;
    }

    @Override
    public void publish(Event event) {
        events = events.append(event);
    }
}
