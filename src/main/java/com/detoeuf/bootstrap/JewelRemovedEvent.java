package com.detoeuf.bootstrap;

public class JewelRemovedEvent implements Event {
    private final Jewel jewel;

    public JewelRemovedEvent(Jewel jewel) {
        this.jewel = jewel;
    }

    public Jewel getJewel() {
        return jewel;
    }
}
