package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class EventDispatcher {
    private final EventBus eventBus;

    public EventDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void dispatch(List<Event> events) {
        eventBus.publish(events);
    }
}
