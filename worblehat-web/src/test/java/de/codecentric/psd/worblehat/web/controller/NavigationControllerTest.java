package de.codecentric.psd.worblehat.web.controller;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NavigationControllerTest {

    @Test
    public void shouldNavigateToHome() throws Exception {
        String navigateTo = new NavigationController().home();

        assertThat(navigateTo, is("home"));
    }
}