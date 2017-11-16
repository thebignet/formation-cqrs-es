package com.detoeuf.bootstrap;

import io.vavr.Tuple;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventBusTest {

    @Test
    void shouldStoreEventWhenJewelAddedToCart() {
        //Given
        EventStore eventStore = new EventStore();
        EventBus eventBus = new EventBus(eventStore);
        Cart cart = new Cart(List.empty());
        //When
        eventBus.publish(cart.addJewel(new Jewel("a")));
        //Then
        assertThat(eventStore.getAll()).containsExactly(new JewelAddedEvent(new Jewel("a")));
    }

    @Test
    void shouldUpdateCartDescriptionWhenJewelAddedToCart() {
        //Given
        EventStore eventStore = new EventStore();
        EventBus eventBus = new EventBus(eventStore);
        CartDescription cartDescription = new CartDescription();
        eventBus.subscribe(cartDescription);
        Cart cart = new Cart(List.empty());
        //When
        eventBus.publish(cart.addJewel(new Jewel("a")));
        //Then
        assertThat(cartDescription.getContent()).containsExactly(Tuple.of(new Jewel("a"), 1));
    }
}
