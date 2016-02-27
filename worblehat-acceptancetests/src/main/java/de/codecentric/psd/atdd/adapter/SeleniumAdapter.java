package de.codecentric.psd.atdd.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import de.codecentric.psd.atdd.adapter.wrapper.Page;
import de.codecentric.psd.atdd.adapter.wrapper.PageElement;
import de.codecentric.psd.atdd.adapter.wrapper.WebTable;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.ScenarioType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Itegrates Selenium into the tests.
 */
public class SeleniumAdapter {
	private WebDriver driver;

	@BeforeStories
	public void initSelenium() throws MalformedURLException {
		
		String seleniumProvider = Config.getSeleniumProvider();
		if (seleniumProvider.equalsIgnoreCase("local")) {
			driver = new FirefoxDriver();
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
	public List<Map<String, String>> getTableContent(PageElement pageElement) {
		WebElement table = driver.findElement(By.className(pageElement.getElementId()));
		return new WebTable(table).getContent();
	}

	public void clickOnPageElement(PageElement pageElement){
		WebElement element = driver.findElement(By.id(pageElement.getElementId()));
		element.click();
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

	public void waitUntilPageContainsId(final String id) throws InterruptedException {
		new WebDriverWait(driver, 10)
		  .until(new ExpectedCondition<WebElement>(){
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id(id));
			}});
	}


	public String getTextFromElement(PageElement pageElement) {
		WebElement element = driver.findElement(By.id(pageElement.getElementId()));
		return element.getText();
	}
}
