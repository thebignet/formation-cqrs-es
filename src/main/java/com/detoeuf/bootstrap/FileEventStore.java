package com.detoeuf.bootstrap;

import io.vavr.collection.List;

public class FileEventStore implements EventStore {

    @Override
    public void appendAll(List<Event> events) {
        //this.events = this.events.appendAll(events);
    }

    @Override
    public List<Event> getAll() {
        return List.empty();
    }
}
