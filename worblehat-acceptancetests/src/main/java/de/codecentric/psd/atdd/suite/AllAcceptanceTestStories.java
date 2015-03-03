package de.codecentric.psd.atdd.suite;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;

import java.util.List;

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
import org.jbehave.core.steps.guice.GuiceStepsFactory;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;
import org.jbehave.web.selenium.WebDriverHtmlOutput;
import org.junit.runner.RunWith;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

/**
 * <p>
 * {@link Embeddable} class to run multiple textual stories via JUnit.
 * </p>
 * <p>
 * Stories are specified in classpath and correspondingly the
 * {@link LoadFromClasspath} story loader is configured.
 * </p>
 */

@RunWith(JUnitReportingRunner.class)
public class AllAcceptanceTestStories extends JUnitStories {

	public AllAcceptanceTestStories() {
		configuredEmbedder().embedderControls()
				.doGenerateViewAfterStories(true)
				.doIgnoreFailureInStories(false).doIgnoreFailureInView(false)
				.useThreads(1).useStoryTimeoutInSecs(60);

		ContextView contextView = new LocalFrameContextView().sized(640, 120);
		SeleniumContext seleniumContext = new SeleniumContext();
		Format[] formats = new Format[] {
				new SeleniumContextOutput(seleniumContext), CONSOLE,
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

	private Injector createInjector() {
		return Guice.createInjector(new StepsModule());
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		return new GuiceStepsFactory(configuration(), createInjector());
	}

	private static void recastExceptionWhenFindingSteps(Exception e) {
		e.printStackTrace();
		throw new RuntimeException("Could not find Step classes", e);
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

	/**
	 * Defines the classes that contain the Steps of the Scenarios.
	 */
	public static class StepsModule extends AbstractModule {

		@Override
		protected void configure() {
			List<String> stepNames = new StoryFinder().findPaths(
					codeLocationFromClass(this.getClass()),
					"**/step/**/*.class", "");
			stepNames.addAll(new StoryFinder().findPaths(
					codeLocationFromClass(this.getClass()),
					"**/library/**/*.class", ""));
			try {
				for (String stepName : stepNames) {

					String className = convertFilepathToClassname(stepName);
					if (!stepName.contains("$")) {
						bind(Class.forName(className)).in(Scopes.SINGLETON);
					}
				}
			} catch (ClassNotFoundException e) {
				recastExceptionWhenFindingSteps(e);
			}
		}

		private String convertFilepathToClassname(String stepName) {
			return stepName.replaceAll("/", ".").substring(0,
					stepName.length() - ".class".length());
		}
	}

}
