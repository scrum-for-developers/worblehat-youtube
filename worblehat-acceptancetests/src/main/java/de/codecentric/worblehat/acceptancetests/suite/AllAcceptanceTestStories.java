package de.codecentric.worblehat.acceptancetests.suite;

import de.codecentric.Application;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

/**
 * <p>
 * {@link Embeddable} class to run multiple textual stories via JUnit.
 * </p>
 * <p>
 * Stories are specified in classpath and correspondingly the
 * {@link LoadFromClasspath} story loader is configured.
 * </p>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AllAcceptanceTestStories extends JUnitStories {

	@Autowired
	private ApplicationContext applicationContext;

	public AllAcceptanceTestStories(){
		initJBehaveConfiguration();
	}

	@Override
	public InjectableStepsFactory stepsFactory(){
		return new SpringStepsFactory(configuration(), applicationContext);
	}

	@Override
	protected List<String> storyPaths() {
		String singleStoryName = System.getProperty("jbehave.story");
		String includePattern = "**/*.story";
		if (singleStoryName != null && singleStoryName.endsWith(".story")) {
			includePattern = "**/" + singleStoryName;
		}
		return new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()), includePattern,
				"**/excluded*.story");
	}
	


	private void initJBehaveConfiguration() {
		configuredEmbedder().embedderControls()
				.doGenerateViewAfterStories(true)
				.doIgnoreFailureInStories(false).doIgnoreFailureInView(false)
				.useThreads(1).useStoryTimeoutInSecs(600);
		SeleniumContext seleniumContext = new SeleniumContext();
		Format[] formats = new Format[] {
				new SeleniumContextOutput(seleniumContext), Format.CONSOLE,
				WebDriverHtmlOutput.WEB_DRIVER_HTML };
		StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
				.withCodeLocation(
						codeLocationFromClass(AllAcceptanceTestStories.class))
				.withFailureTrace(true).withFailureTraceCompression(true)
				.withDefaultFormats().withFormats(formats);

		 Configuration configuration = new SeleniumConfiguration()
		 .useSeleniumContext(seleniumContext)
		 .useFailureStrategy(new FailingUponPendingStep())
		 .useStoryControls(
		 new StoryControls().doResetStateBeforeScenario(false))
		 .useStoryLoader(
		 new LoadFromClasspath(AllAcceptanceTestStories.class))
		 .useStoryReporterBuilder(reporterBuilder);
		 useConfiguration(configuration);

	}

}
