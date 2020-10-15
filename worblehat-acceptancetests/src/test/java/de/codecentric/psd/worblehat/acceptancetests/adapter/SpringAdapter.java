package de.codecentric.psd.worblehat.acceptancetests.adapter;

import de.codecentric.psd.Worblehat;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@CucumberContextConfiguration
@SpringBootTest(
    classes = {Worblehat.class, SpringAdapter.SpringConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAdapter {

  @Before
  public void initializeSpringContext() {
    // just a marker method to make cucumber aware

  }

  @Configuration
  @ComponentScan(basePackages = "de.codecentric.psd.worblehat.acceptancetests")
  public static class SpringConfig {}
}
