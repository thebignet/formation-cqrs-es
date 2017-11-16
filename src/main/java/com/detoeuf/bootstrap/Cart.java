package com.detoeuf.bootstrap;

import io.vavr.collection.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


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
        Match(event).of(
                Case($(instanceOf(CartSubmittedEvent.class)), x -> submitted = true)
        );
    }
}
