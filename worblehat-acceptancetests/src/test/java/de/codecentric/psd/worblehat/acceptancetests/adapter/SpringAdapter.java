package de.codecentric.psd.worblehat.acceptancetests.adapter;

import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.codecentric.psd.Worblehat;

@SpringBootTest(classes = {Worblehat.class, SpringAdapter.SpringConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAdapter {

    @Before
    public void initializeSpringContext() {
        // just a marker method to make cucumber aware

    }

    @Configuration
    @ComponentScan(basePackages = "de.codecentric.psd.worblehat.acceptancetests")
    public static class SpringConfig {
    }
}
