package com.detoeuf.bootstrap;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class CartTest {
    /* Submit */

    @Test
    void shouldRaiseCartSubmittedWhenSubmit() {
        //Given
        AggregateId aggregateId = AggregateId.generate();
        Cart cart = Cart.fromEvents(aggregateId, List.of(new JewelAddedEvent(aggregateId, new Jewel("a"), SequenceNumber.initial().next())));
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).hasSize(1);
        assertThat(events.head()).isInstanceOf(CartSubmittedEvent.class);
    }

    @Test
    void shouldNotRaiseEventWhenCartAlreadySubmitted() {
        //Given
        AggregateId aggregateId = AggregateId.generate();
        Cart cart = Cart.fromEvents(aggregateId, List.of(
                new JewelAddedEvent(aggregateId, new Jewel("a"), SequenceNumber.initial().next()),
                new CartSubmittedEvent(aggregateId, SequenceNumber.initial().next()))
        );
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).isEmpty();
    }

    @Test
    void shouldNotRaiseEventWhenCartSubmittedTwice() {
        //Given
        AggregateId aggregateId = AggregateId.generate();
        Cart cart = Cart.fromEvents(aggregateId, List.of(new JewelAddedEvent(aggregateId, new Jewel("a"), SequenceNumber.initial().next())));
        cart.submit();
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).isEmpty();
    }

    @Test
    void shouldNotRaiseEventWhenEmptyCartSubmitted() {
        //Given
        Cart cart = Cart.pickup();
        //When
        assertThatThrownBy(cart::submit).isInstanceOf(IllegalStateException.class);
    }

    /* Jewel */

    @Test
    void shouldRaiseEventWhenJewelAdded() {
        //Given
        Cart cart = Cart.pickup();
        //When
        List<Event> events = cart.addJewel(new Jewel("a"));
        //Then
        assertThat(events).hasSize(1);
        assertThat(events.head()).isInstanceOf(JewelAddedEvent.class);
    }

    @Test
    void shouldThrowWhenJewelAddedInSubmittedState() {
        //Given
        AggregateId aggregateId = AggregateId.generate();
        Cart cart = Cart.fromEvents(aggregateId, List.of(new CartSubmittedEvent(aggregateId, SequenceNumber.initial().next())));
        assertThatThrownBy(() -> cart.addJewel(new Jewel("a"))).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldAddJewelToContentWhenJewelAdded() {
        //Given
        Cart cart = Cart.pickup();
        //When
        cart.addJewel(new Jewel("a"));
        //Then
        assertThat(cart.listContent()).hasSize(1);
    }

}
