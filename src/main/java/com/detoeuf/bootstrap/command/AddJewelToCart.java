package com.detoeuf.bootstrap.command;

import com.detoeuf.bootstrap.Jewel;

public class AddJewelToCart implements Command {

    private final Jewel jewel;

    public AddJewelToCart(Jewel jewel) {
        this.jewel = jewel;
    }
}
