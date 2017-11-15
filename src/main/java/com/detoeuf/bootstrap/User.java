package com.detoeuf.bootstrap;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {

    String name;

    public String bonjour() {
        return "bonjour " + name;
    }
}
