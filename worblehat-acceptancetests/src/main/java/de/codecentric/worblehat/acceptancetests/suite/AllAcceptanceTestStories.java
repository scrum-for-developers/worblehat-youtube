package de.codecentric.worblehat.acceptancetests.suite;

import com.thoughtworks.paranamer.NullParanamer;
import de.codecentric.psd.worblehat.domain.*;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.FreemarkerViewGenerator;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.*;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@TestPropertySource( properties = {"spring.datasource.driver-class-name=com.mysql.jdbc.Driver",
"spring.datasource.url=jdbc:mysql://localhost:3306/worblehat_test",
"liquibase.change-log=classpath:master.xml",
"spring.datasource.username=worblehat",
"spring.datasource.password=worblehat"} )

@EnableJpaRepositories("de.codecentric.psd.worblehat.domain")
@EntityScan("de.codecentric.psd.worblehat.domain")
@EnableTransactionManagement
@ComponentScan(
		basePackages = {
				"de.codecentric.worblehat.acceptancetests",
				"de.codecentric.psd.worblehat.domain"})
public class AllAcceptanceTestStories extends JUnitStories {
	
	@Autowired
	private ApplicationContext applicationContext;

	public AllAcceptanceTestStories(){
	}

	@Override
	public Configuration configuration() {

		if (!hasConfiguration()) {

			// prepare SeleniumContext
			SeleniumContext seleniumContext = new SeleniumContext();
			Format[] formats = new Format[] {
					new SeleniumContextOutput(seleniumContext), Format.CONSOLE,
					WebDriverHtmlOutput.WEB_DRIVER_HTML };

			// prepare ReportBuilder
			StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
					.withCodeLocation(
							codeLocationFromClass(AllAcceptanceTestStories.class))
					.withFailureTrace(true).withFailureTraceCompression(true)
					.withDefaultFormats().withFormats(formats);

			// general JBehave configuration
			Configuration configuration = new SeleniumConfiguration()
					.useSeleniumContext(seleniumContext)
					.useFailureStrategy(new FailingUponPendingStep())
					.useStoryControls(
							new StoryControls().doResetStateBeforeScenario(false))
					.useStoryLoader(
							new LoadFromClasspath(AllAcceptanceTestStories.class))
					.useStoryReporterBuilder(reporterBuilder);

			// add configuration from MostUsefulConfiguration
			configuration
					.useKeywords(new LocalizedKeywords())
					.useStoryParser(new RegexStoryParser(configuration.keywords(), configuration.storyLoader(), configuration.tableTransformers()))
					.usePendingStepStrategy(new PassingUponPendingStep())
					.useStepCollector(new MarkUnmatchedStepsAsPending())
					.useStepFinder(new StepFinder())
					.useStepPatternParser(new RegexPrefixCapturingPatternParser())
					.useStepMonitor(new SilentStepMonitor())
					.useStepdocReporter(new PrintStreamStepdocReporter())
					.useParanamer(new NullParanamer())

					// use column headers of example table to identify the parameter instead of the name in the step itself
					.useParameterControls(new ParameterControls().useDelimiterNamedParameters(true))
					.useViewGenerator(new FreemarkerViewGenerator());

			useConfiguration(configuration);

		}

		return super.configuration();
	}

	@Override
	public Embedder configuredEmbedder() {
		Embedder configuredEmbedder = super.configuredEmbedder();

		configuredEmbedder.embedderControls()

				// collect failures and report them in batch
				.doBatch(false)
				.doGenerateViewAfterStories(true)
				.doIgnoreFailureInStories(false)
				.doIgnoreFailureInView(false)
				.doSkip(false)
				.doVerboseFailures(false)
				.doVerboseFiltering(false)
				.useThreads(1);

		return configuredEmbedder;

	}

	@Override
	public InjectableStepsFactory stepsFactory(){
		// just some sanity check to fail fast, while working on the Spring plumbing
		boolean seleniumAdapter = applicationContext.containsBeanDefinition("SeleniumAdapter");
		boolean library = applicationContext.containsBeanDefinition("Library");
		boolean bookList = applicationContext.containsBeanDefinition("BookList");
		if (!seleniumAdapter || !library || !bookList) {
			throw new RuntimeException("Some essential beans are missing. SeleniumAdapter: " + seleniumAdapter +
					", Library: " + library +
							", BookList: " + bookList);
		}

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

}
