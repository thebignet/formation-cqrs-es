package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class Cart {
    private final EventPublisher eventPublisher;
    private final List<Event> history;
    private boolean submitted = false;

    public Cart(List<Event> history, EventPublisher eventPublisher) {
        this.history = history;
        this.eventPublisher = eventPublisher;
        this.submitted = !history.find(e -> e instanceof CartSubmittedEvent).isEmpty();
    }

    public void submit() {
        if (!submitted) {
            eventPublisher.publish(new CartSubmittedEvent());
            submitted = true;
        }
    }
}
