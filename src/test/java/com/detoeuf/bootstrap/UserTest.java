package com.detoeuf.bootstrap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    void nameShouldReturnUserName() {
        //Given
        final User user = User.of("jean");
        //When
        final String name = user.getName();
        //Then
        assertThat(name).isEqualTo("jean");
    }

    @ParameterizedTest
    @ValueSource(strings = { "Jean", "Pierre", "Claude" })
    void shouldSayBonjour(String name) {
        //Given
        final User user = User.of(name);
        //When
        final String salutation = user.bonjour();
        //Then
        assertThat(salutation)
            .startsWith("bonjour")
            .endsWith(name);
    }

}
