package de.codecentric.psd.worblehat;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    strict = true,
    stepNotifications = true,
//    tags = "@Focus",
    plugin = {
      "pretty",
      "html:target/cucumber.html",
      "junit:target/cucumber.xml",
      "json:target/cucumber-report.json"
    },
    publish = false)
public class AcceptanceTestsIT {}
