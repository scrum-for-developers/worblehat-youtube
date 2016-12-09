package de.codecentric.psd.atdd.adapter;

import de.codecentric.psd.atdd.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.atdd.adapter.wrapper.Page;
import de.codecentric.psd.atdd.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.ScenarioType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Itegrates Selenium into the tests.
 */
@Controller
public class SeleniumAdapter {
	private WebDriver driver;
	private Logger LOGGER = LoggerFactory.getLogger(SeleniumAdapter.class);

	@BeforeStories
	public void initSelenium() throws MalformedURLException {
		String seleniumProvider = Config.getSeleniumProvider();
		if (seleniumProvider.equalsIgnoreCase("local")) {
			try{
				System.setProperty("webdriver.chrome.driver","worblehat-acceptancetests/src/main/resources/chromedriver_mac64");
				driver = new ChromeDriver();
			} catch (Exception e){
				LOGGER.error("Error initializing ChromeDriver", e);
			}
		} else {
			DesiredCapabilities capabilities = null;
			String browser = Config.getBrowser();
			if (browser.equalsIgnoreCase("firefox")) {
				capabilities = DesiredCapabilities.firefox();
			} else if (browser.equalsIgnoreCase("ie")) {
				capabilities = DesiredCapabilities.internetExplorer();
			} else if (browser.equalsIgnoreCase("chrome")) {
				 capabilities = DesiredCapabilities.chrome();
			}
			capabilities.setCapability("version", Config.getBrowserVersion());
			capabilities.setCapability("platform", Config.getBrowserOS());
			capabilities.setCapability("name", Config.getTestDescription());
			capabilities.setCapability("public", true);
			capabilities.setCapability("restricted-public-info", true);

			driver = new RemoteWebDriver(
					new URL("http://"+Config.getSauceAccount()+":"+Config.getSauceKey() + "@" +
							Config.getSeleniumServer()), capabilities);
			if (driver==null) {
				throw new RuntimeException("Could not create RemoteWebDriver with desired capabilities.\n" +
						"browser: "+browser+"\n" +
						"version: "+Config.getBrowserVersion()+"\n" +
						"platform: "+Config.getBrowserOS());
			}
			System.out.println("To access the job page, please go to http://saucelabs.com/jobs/"+((RemoteWebDriver)driver).getSessionId());

		}

		// // Find the text input element by its name
		// WebElement element = driver.findElement(By.name("q"));
		//
		// // Enter something to search for
		// element.sendKeys("Cheese!");
		//
		// // Now submit the form. WebDriver will find the form for us from the
		// // element
		// element.submit();

	}

	public void gotoPage(Page page){
		driver.get(Config.getApplicationURL() + "/" + page.getUrl());
	}

	public void typeIntoField(String id, String value) {
		WebElement element = driver.findElement(By.id(id));
		element.clear();
		element.sendKeys(value);
	}
	public HtmlBookList getTableContent(PageElement pageElement) {
		WebElement table = driver.findElement(By.className(pageElement.getElementId()));
		return new HtmlBookList(table);
	}

	public void clickOnPageElement(PageElement pageElement){
		WebElement element = driver.findElement(By.id(pageElement.getElementId()));
		element.click();
	}

	public List<String> findAllStringsForElement(PageElement pageElement){
		List<WebElement> webElements = driver.findElements(By.className(pageElement.getElementId()));
		List<String> strings = new ArrayList<>();
		for (WebElement element : webElements){
			strings.add(element.getText());
		}
		return strings;
	}

	@AfterStories
	public void afterStories() {
		// Close the browser
		driver.quit();
	}

	@AfterScenario(uponType=ScenarioType.EXAMPLE)
	// equivalent to @AfterScenario(uponOutcome=AfterScenario.Outcome.ANY)
	public void afterAnyScenario() {
		Options driverOptions = driver.manage();
		driverOptions.deleteAllCookies();
	}



	public String getTextFromElement(PageElement pageElement) {
		WebElement element = driver.findElement(By.id(pageElement.getElementId()));
		return element.getText();
	}
}
