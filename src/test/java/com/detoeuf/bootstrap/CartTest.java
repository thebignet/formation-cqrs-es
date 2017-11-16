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
        Cart cart = new Cart(List.of(new JewelAddedEvent(new Jewel("a"))));
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).hasSize(1);
        assertThat(events.head()).isInstanceOf(CartSubmittedEvent.class);
    }

    @Test
    void shouldNotRaiseEventWhenCartAlreadySubmitted() {
        //Given
        Cart cart = new Cart(List.of(new JewelAddedEvent(new Jewel("a")), new CartSubmittedEvent()));
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).isEmpty();
    }

    @Test
    void shouldNotRaiseEventWhenCartSubmittedTwice() {
        //Given
        Cart cart = new Cart(List.of(new JewelAddedEvent(new Jewel("a"))));
        cart.submit();
        //When
        List<Event> events = cart.submit();
        //Then
        assertThat(events).isEmpty();
    }

    @Test
    void shouldNotRaiseEventWhenEmptyCartSubmitted() {
        //Given
        Cart cart = new Cart(List.empty());
        //When
        assertThatThrownBy(cart::submit).isInstanceOf(IllegalStateException.class);
    }

    /* Jewel */

    @Test
    void shouldRaiseEventWhenJewelAdded() {
        //Given
        Cart cart = new Cart(List.empty());
        //When
        List<Event> events = cart.addJewel(new Jewel("a"));
        //Then
        assertThat(events).hasSize(1);
        assertThat(events.head()).isInstanceOf(JewelAddedEvent.class);
    }

    @Test
    void shouldThrowWhenJewelAddedInSubmittedState() {
        //Given
        Cart cart = new Cart(List.of(new CartSubmittedEvent()));
        assertThatThrownBy(() -> cart.addJewel(new Jewel("a"))).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldAddJewelToContentWhenJewelAdded() {
        //Given
        Cart cart = new Cart(List.empty());
        //When
        cart.addJewel(new Jewel("a"));
        //Then
        assertThat(cart.listContent()).hasSize(1);
    }

}
