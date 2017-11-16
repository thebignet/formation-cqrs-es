package com.detoeuf.bootstrap;

import org.junit.jupiter.api.Test;

import static io.vavr.API.Map;
import static io.vavr.API.Tuple;
import static org.assertj.core.api.Assertions.assertThat;

class CartDescriptionTest {

    @Test
    void shouldHaveAJewelInDescriptionWhenJewelAdded() {
        //Given
        CartDescription cartDescription = new CartDescription();
        //When
        cartDescription.handle(new JewelAddedEvent(new Jewel("a")));
        //Then
        assertThat(cartDescription.getContent()).contains(Tuple(new Jewel("a"), 1));
    }

    @Test
    void shouldHaveEmptyDescriptionWhenOneJewelInCartAndRemoved() {
        //Given
        CartDescription cartDescription = new CartDescription(Map(new Jewel("a"), 1));
        //When
        cartDescription.handle(new JewelRemovedEvent(new Jewel("a")));
        //Then
        assertThat(cartDescription.getContent()).isEmpty();
    }

}