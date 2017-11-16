package com.detoeuf.bootstrap;

public class JewelAddedEvent implements Event {

    private final Jewel jewel;

    public JewelAddedEvent(Jewel jewel) {
        this.jewel = jewel;
    }


    public Jewel getJewel() {
        return this.jewel;
    }
}
