package com.detoeuf.bootstrap;

import com.detoeuf.bootstrap.command.AddJewelToCart;
import io.vavr.Tuple;
import io.vavr.collection.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EventBusTest {
    private File temporaryFolder;

    @BeforeEach
    public void createFolder(){

        File temporaryFolder = new File("./"+ UUID.randomUUID());
        temporaryFolder.mkdir();
        this.temporaryFolder = temporaryFolder;
    }

    @AfterEach
    public void cleanup() throws IOException {
        FileUtils.deleteDirectory(this.temporaryFolder);
    }

    @Test
    void shouldStoreEventWhenJewelAddedToCart() {
        //Given
        EventStore eventStore = new FileEventStore(temporaryFolder);
        EventBus eventBus = new EventBus(eventStore);
        Cart cart = Cart.pickup();
        //When
        eventBus.publish(cart.addJewel(new Jewel("a")));
        //Then
        assertThat(eventStore.getEventsOfAggregate(cart.getAggregateId())).containsExactly(new JewelAddedEvent(cart.getAggregateId(), new Jewel("a"), SequenceNumber.initial().next()));
    }

    @Test
    void shouldStoreEventsWhenJewelAddedToCartAndSubmitted() {
        //Given
        EventStore eventStore = new FileEventStore(temporaryFolder);
        EventBus eventBus = new EventBus(eventStore);
        Cart cart = Cart.pickup();
        //When
        eventBus.publish(cart.addJewel(new Jewel("a")));
        eventBus.publish(cart.submit());
        //Then
        assertThat(eventStore.getEventsOfAggregate(cart.getAggregateId())).containsExactly(
                new JewelAddedEvent(cart.getAggregateId(), new Jewel("a"), SequenceNumber.initial().next()),
                new CartSubmittedEvent(cart.getAggregateId(), SequenceNumber.initial().next().next())
        );
    }

    @Test
    void shouldNotApplySameCommandOnTheSameCart() {
        //Given
        EventStore eventStore = new FileEventStore(temporaryFolder);
        EventBus eventBus = new EventBus(eventStore);
        Cart cartA = Cart.pickup();
        Cart cartB = Cart.fromEvents(cartA.getAggregateId(), List.empty());
        //When
        eventBus.publish(cartA.addJewel(new Jewel("a")));
        assertThatThrownBy(() -> eventBus.publish(cartB.addJewel(new Jewel("a")))).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldCallHandlersToUpdateCartDescriptionWhenJewelAddedToCart() {
        //Given
        EventStore eventStore = new FileEventStore(temporaryFolder);
        EventBus eventBus = new EventBus(eventStore);
        CartDescription cartDescription = new CartDescription();
        eventBus.subscribe(cartDescription);
        Cart cart = Cart.pickup();
        //When
        eventBus.publish(cart.addJewel(new Jewel("a")));
        //Then
        assertThat(cartDescription.getContent()).containsExactly(Tuple.of(new Jewel("a"), 1));
    }

    @Test
    void shouldUpdateCartDescriptionWhenJewelAddedToCart() {
        //Given
        EventStore eventStore = new FileEventStore(temporaryFolder);
        EventBus eventBus = new EventBus(eventStore);
        CartDescription cartDescription = new CartDescription();
        eventBus.subscribe(cartDescription);
        Cart cart = Cart.pickup();
        CommandDispatcher commandDispatcher = new CommandDispatcher(eventBus);
        commandDispatcher.register(AddJewelToCart.class, command -> cart.addJewel(command.getJewel()));
        //When
        commandDispatcher.dispatch(new AddJewelToCart(new Jewel("a")));
        //Then
        assertThat(cartDescription.getContent()).containsExactly(Tuple.of(new Jewel("a"), 1));
    }
}
