package com.detoeuf.bootstrap;


import io.vavr.Tuple;
import io.vavr.collection.Map;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class CartDescription {
    private Map<Jewel, Integer> jewels;

    public CartDescription(Map<Jewel, Integer> jewels) {
        this.jewels = jewels;
    }

    public CartDescription() {
        this.jewels = Map();
    }

    public void handle(Event event) {
        Match(event).of(
                Case($(instanceOf(JewelAddedEvent.class)), this::addJewel),
                Case($(instanceOf(JewelRemovedEvent.class)), this::removeJewel)
        );
    }

    private Void addJewel(JewelAddedEvent event) {
        Jewel jewel = event.getJewel();
        jewels = jewels.put(
                Tuple.of(jewel, 1),
                (x, y) -> x + y);
        return null;
    }

    private Void removeJewel(JewelRemovedEvent event) {
        Jewel jewel = event.getJewel();
        Integer previousValue = jewels.get(jewel).getOrElseThrow(IllegalStateException::new);
        Integer newValue = previousValue - 1;
        if (newValue == 0) {
            jewels = jewels.remove(jewel);
        } else {
            jewels = jewels.put(
                    Tuple.of(jewel, -1),
                    (x, y) -> x + y);
        }
        return null;
    }

    public Map<Jewel, Integer> getContent() {
        return jewels;
    }
}
