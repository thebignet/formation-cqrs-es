package com.detoeuf.bootstrap;

import java.util.Objects;

public class JewelAddedEvent implements Event {

    private final Jewel jewel;

    public JewelAddedEvent(Jewel jewel) {
        this.jewel = jewel;
    }

    public Jewel getJewel() {
        return this.jewel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JewelAddedEvent that = (JewelAddedEvent) o;
        return Objects.equals(jewel, that.jewel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jewel);
    }
}
