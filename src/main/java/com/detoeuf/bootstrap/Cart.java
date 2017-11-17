package com.detoeuf.bootstrap;

import io.vavr.collection.List;

import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


public class Cart {
    private final UUID id;
    private CartState state;

    private Cart(UUID id, List<Event> history) {
        this.id = id;
        this.state = history.foldLeft(CartState.initial(), CartState::apply);
    }

    public static Cart pickup() {
        return new Cart(UUID.randomUUID(), List.empty());
    }

    public static Cart fromEvents(UUID id, List<Event> history) {
        return new Cart(id, history);
    }

    public List<Event> submit() {
        if (state.jewels.isEmpty()) {
            throw new IllegalStateException();
        }
        if (!state.submitted) {
            CartSubmittedEvent event = new CartSubmittedEvent();
            state = state.apply(event);
            return List.of(event);
        }
        return List.empty();
    }

    public List<Event> addJewel(Jewel jewel) {
        if (state.submitted) {
            throw new IllegalStateException();
        }
        JewelAddedEvent event = new JewelAddedEvent(jewel);
        state = state.apply(event);
        return List.of(event);
    }

    public List<Jewel> listContent() {
        return state.jewels;
    }

    private static class CartState {
        private final boolean submitted;
        private final List<Jewel> jewels;

        CartState(boolean submitted, List<Jewel> jewels) {
            this.submitted = submitted;
            this.jewels = jewels;
        }

        static CartState initial() {
            return new CartState(false, List.empty());
        }

        private CartState apply(Event event) {
            return Match(event).of(
                    Case($(instanceOf(CartSubmittedEvent.class)), this::submitted),
                    Case($(instanceOf(JewelAddedEvent.class)), this::addJewel)
            );
        }

        private CartState submitted() {
            return new CartState(true, jewels);
        }


        private CartState addJewel(JewelAddedEvent event) {
            return new CartState(submitted, jewels.append(event.getJewel()));
        }


    }

}
