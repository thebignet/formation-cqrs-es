package com.detoeuf.bootstrap;

import java.util.Objects;

public class Jewel {
    private final String id;

    public Jewel(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jewel jewel = (Jewel) o;
        return Objects.equals(id, jewel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
