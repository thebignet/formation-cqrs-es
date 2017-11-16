package com.detoeuf.bootstrap;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CartTest {

    @Test
    void shouldRaiseCartSubmittedWhenSubmit() {
        //Given
        TestEventPublisher eventPublisher = new TestEventPublisher();
        Cart cart = new Cart(List.empty(), eventPublisher);
        //When
        cart.submit();
        //Then
        List<Event> events = eventPublisher.listPublishedEvents();
        assertThat(events).hasSize(1);
        assertThat(events.head()).isInstanceOf(CartSubmittedEvent.class);
    }

    @Test
    void shouldNotRaiseEventWhenCartAlreadySubmitted() {
        //Given
        TestEventPublisher eventPublisher = new TestEventPublisher();
        Cart cart = new Cart(List.of(new CartSubmittedEvent()), eventPublisher);
        //When
        cart.submit();
        //Then
        List<Event> events = eventPublisher.listPublishedEvents();
        assertThat(events).isEmpty();
    }

}
