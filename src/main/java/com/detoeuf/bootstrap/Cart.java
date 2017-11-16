package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class Cart {
    private final EventPublisher eventPublisher;
    private final List<Event> history;

    public Cart(List<Event> history, EventPublisher eventPublisher) {
        this.history = history;
        this.eventPublisher = eventPublisher;
    }

    public void submit() {
        if (history.filter(e -> e instanceof CartSubmittedEvent).isEmpty()) {
            eventPublisher.publish(new CartSubmittedEvent());
        }
    }
}
