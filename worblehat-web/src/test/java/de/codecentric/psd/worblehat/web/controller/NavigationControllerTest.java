package de.codecentric.psd.worblehat.web.controller;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NavigationControllerTest {

    private NavigationController navigationController;

    @Before
    public void setUp() throws Exception {
        navigationController = new NavigationController();
    }

    @Test
    public void shouldNavigateToHome() throws Exception {
        String navigateTo = navigationController.home();
        assertThat(navigateTo, is("home"));
    }
}