package de.codecentric.psd.worblehat;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    // run dedicated feature files
    // features = {"src/test/resources/de/codecentric/psd/worblehat/features/book/Remove
    // Book.feature"},
    // run feature identified by a tag expression
    tags = "@Focus",
    strict = true,
    stepNotifications = true,
    plugin = {
      "pretty",
      "html:target/cucumber.html",
      "junit:target/cucumber.xml",
      "json:target/cucumber-report.json"
    },
    publish = false)
public class TagFocusIT {}
