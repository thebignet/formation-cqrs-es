package com.detoeuf.bootstrap;

import io.vavr.collection.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


public class Cart {
    private final EventPublisher eventPublisher;
    private boolean submitted;
    private List<Jewel> jewels;

    public Cart(List<Event> history, EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.submitted = false;
        this.jewels = List.empty();
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
                Case($(instanceOf(CartSubmittedEvent.class)), x -> submitted = true),
                Case($(instanceOf(JewelAddedEvent.class)), x -> jewels = jewels.append(x.getJewel()))
        );
    }

    public void addJewel(Jewel jewel) {
        if (submitted) {
            throw new IllegalStateException();
        }
        JewelAddedEvent event = new JewelAddedEvent(jewel);
        eventPublisher.publish(event);
        apply(event);
    }

    public List<Jewel> listContent() {
        return jewels;
    }
}
