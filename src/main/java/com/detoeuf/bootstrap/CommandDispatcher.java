package com.detoeuf.bootstrap;

import com.detoeuf.bootstrap.command.Command;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import java.util.function.Function;

import static io.vavr.API.Map;

public class CommandDispatcher {


    private final EventBus eventBus;
    private Map<Class, Function<Command, List<Event>>> handlers;

    public CommandDispatcher(EventBus eventBus) {
        this.eventBus = eventBus;
        this.handlers = Map();
    }

    public <C extends Command> void register(Class<C> type, Function<C, List<Event>> commandHandler) {
        Function<Command, List<Event>> castedHandler = (Function<Command, List<Event>>) commandHandler;
        this.handlers = handlers.put(type, castedHandler);
    }

    public void dispatch(Command c) {
        Option<List<Event>> events = handlers.get(c.getClass())
            .map(f -> f.apply(c));
        eventBus.publish(events.getOrElse(List.empty()));
    }
}
