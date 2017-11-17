package com.detoeuf.bootstrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SequenceNumber {
    private final int number;

    @JsonCreator
    public SequenceNumber(@JsonProperty("number") int number) {
        this.number = number;
    }

    public static SequenceNumber initial() {
        return new SequenceNumber(0);
    }

    public SequenceNumber next() {
        return new SequenceNumber(number + 1);
    }

    public boolean isNext(SequenceNumber sequenceNumber) {
        return sequenceNumber.equals(this.next());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequenceNumber that = (SequenceNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public int getNumber() {
        return number;
    }
}
