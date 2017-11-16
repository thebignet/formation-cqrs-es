package com.detoeuf.bootstrap;

import io.vavr.Tuple2;
import io.vavr.collection.List;

public class EventBus {
    private final EventStore eventStore;
    private List<Subscriber> handlers = List.empty();

    public EventBus(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void subscribe(CartDescription cartDescription) {
        this.handlers = handlers.append(cartDescription);
    }

    public void publish(List<Event> events) {
        eventStore.appendAll(events);
        handlers.crossProduct(events).forEach(this::callHandler);
    }

    private void callHandler(Tuple2<Subscriber, Event> tuple) {
        tuple._1.handle(tuple._2);
    }
}
