package com.detoeuf.bootstrap;

import io.vavr.collection.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


public class Cart {
    private final AggregateId aggregateId;
    private CartState state;

    private Cart(AggregateId aggregateId, List<Event> history) {
        this.aggregateId = aggregateId;
        this.state = history.foldLeft(CartState.initial(), CartState::apply);
    }

    public static Cart pickup() {
        return new Cart(AggregateId.generate(), List.empty());
    }

    public static Cart fromEvents(AggregateId aggregateId, List<Event> history) {
        return new Cart(aggregateId, history);
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public List<Event> submit() {
        if (state.jewels.isEmpty()) {
            throw new IllegalStateException();
        }
        if (!state.submitted) {
            CartSubmittedEvent event = new CartSubmittedEvent(aggregateId, state.sequenceNumber.next());
            state = state.apply(event);
            return List.of(event);
        }
        return List.empty();
    }

    public List<Event> addJewel(Jewel jewel) {
        if (state.submitted) {
            throw new IllegalStateException();
        }
        JewelAddedEvent event = new JewelAddedEvent(aggregateId, jewel, state.sequenceNumber.next());
        state = state.apply(event);
        return List.of(event);
    }

    public List<Jewel> listContent() {
        return state.jewels;
    }

    private static class CartState {
        private final boolean submitted;
        private final List<Jewel> jewels;
        private final SequenceNumber sequenceNumber;

        CartState(SequenceNumber sequenceNumber, boolean submitted, List<Jewel> jewels) {
            this.sequenceNumber = sequenceNumber;
            this.submitted = submitted;
            this.jewels = jewels;
        }

        static CartState initial() {
            return new CartState(SequenceNumber.initial(), false, List.empty());
        }

        private CartState apply(Event event) {
            return Match(event).of(
                    Case($(instanceOf(CartSubmittedEvent.class)), this::submitted),
                    Case($(instanceOf(JewelAddedEvent.class)), this::addJewel)
            );
        }

        private CartState submitted(CartSubmittedEvent event) {
            return new CartState(event.getSequenceNumber(), true, jewels);
        }


        private CartState addJewel(JewelAddedEvent event) {
            return new CartState(event.getSequenceNumber(), submitted, jewels.append(event.getJewel()));
        }


    }

}
