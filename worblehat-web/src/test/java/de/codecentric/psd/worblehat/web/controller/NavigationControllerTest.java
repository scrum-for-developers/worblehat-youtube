package de.codecentric.psd.worblehat.web.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class NavigationControllerTest {

    @Test
    void shouldNavigateToHome() throws Exception {
        String navigateTo = new NavigationController().home();

        assertThat(navigateTo, is("home"));
    }
}
