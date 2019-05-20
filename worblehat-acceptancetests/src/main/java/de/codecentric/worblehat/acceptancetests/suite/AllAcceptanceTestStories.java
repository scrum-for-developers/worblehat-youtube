package de.codecentric.worblehat.acceptancetests.suite;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.StepCollector;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

/**
 * <p>
 * {@link Embeddable} class to run multiple textual stories via JUnit.
 * </p>
 * <p>
 * Stories are specified in classpath and correspondingly the
 * {@link LoadFromClasspath} story loader is configured.
 * </p>
 */

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@TestPropertySource
@EnableJpaRepositories("de.codecentric.psd.worblehat.domain")
@EntityScan("de.codecentric.psd.worblehat.domain")
@ComponentScan(
    basePackages = {
        "de.codecentric.worblehat.acceptancetests",
        "de.codecentric.psd.worblehat.domain"})
public class AllAcceptanceTestStories extends ConfigurableEmbedder {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SeleniumAdapter seleniumAdapter;

    static {
        Testcontainers.exposeHostPorts(8080);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AllAcceptanceTestStories.class);

    @ClassRule
    @SuppressWarnings("rawtypes")
    public static final BrowserWebDriverContainer chromeContainer = new BrowserWebDriverContainer<>()
        .withCapabilities(new ChromeOptions())
        .withRecordingMode(RECORD_ALL, new File("./target/"));


    @Before
    public void setup() {
        seleniumAdapter.setDriver(chromeContainer.getWebDriver());
        try {
            Runtime.getRuntime().exec("open " + chromeContainer.getVncAddress());
        } catch (IOException e) {
            // silently fail, if it's not working – e.printStackTrace();
        }
        LOGGER.info("Connect to VNC via %{}", chromeContainer.getVncAddress());
    }


    @Test
    public void run() throws Throwable {
        Embedder embedder = configuredEmbedder();
        try {

            checkForRunningWorblehat();

            if (isSoloApplied(embedder)) {
                LOGGER.info("Found Scenarios or Stories marked as '@Solo', activating Meta-Filter.");
                embedder.useMetaFilters(List.of("+Solo"));
            }
            embedder.runStoriesAsPaths(storyPaths());
        } finally {
            embedder.generateCrossReference();
        }
    }

    private boolean isSoloApplied(Embedder embedder) {
        StoryManager storyManager = embedder.storyManager();

        return storyPaths().stream()
            .map(storyManager::storyOfPath).anyMatch(story -> (
                story.getMeta().hasProperty("Solo") ||
                    story.getScenarios().stream().anyMatch(scenario -> scenario.getMeta().hasProperty("Solo"))
            ));
    }

    private void checkForRunningWorblehat() {
        seleniumAdapter.gotoPage(Page.HOME);
        if (!seleniumAdapter.containsTextOnPage("Worblehat")) {
            throw new IllegalStateException("Worblehat doesn't seem to be running. Please start the application first");
        }
    }

    @Override
    public Configuration configuration() {

        if (!hasConfiguration()) {

            // prepare ReportBuilder
            StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
                .withFailureTrace(true)
                .withFailureTraceCompression(true)
                .withFormats(Format.CONSOLE, Format.HTML, Format.STATS);

            // necessary to match steps correctly that only differ after the last parameter
            // see http://jbehave.org/reference/stable/prioritising-steps.html
            StepFinder.PrioritisingStrategy prioritisingStrategy = new StepFinder.ByLevenshteinDistance();
            StepFinder stepFinder = new StepFinder(prioritisingStrategy);
            StepCollector usefulStepCollector = new MarkUnmatchedStepsAsPending(stepFinder);

            // general JBehave configuration
            Configuration configuration = new MostUsefulConfiguration()
                .useStepCollector(usefulStepCollector)
                .useStoryControls(
                    new StoryControls().doResetStateBeforeScenario(false))
                .useStoryReporterBuilder(reporterBuilder);

            useConfiguration(configuration);

        }

        return super.configuration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
            codeLocationFromClass(this.getClass()), "**/*.story",
            "");
    }

}
