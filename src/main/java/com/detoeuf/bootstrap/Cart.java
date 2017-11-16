package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class Cart {
    private final EventPublisher eventPublisher;
    private boolean submitted = false;

    public Cart(List<Event> history, EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.submitted = false;
        history.forEach(this::apply);
    }

    public void submit() {
        if (!submitted) {
            CartSubmittedEvent event = new CartSubmittedEvent();
            eventPublisher.publish(event);
            apply(event);
        }
    }

    private void apply(Event event) {
        submitted |= event instanceof CartSubmittedEvent;
    }
}
