package de.codecentric.psd.atdd.library;

import java.net.MalformedURLException;
import java.net.URL;

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
 * 
 * @author aek
 *
 */
public class SeleniumAdapter {
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeStories
	public void initSelenium() throws MalformedURLException {
		
		String seleniumProvider = Config.getSeleniumProvider();
		if (seleniumProvider.equalsIgnoreCase("local")) {
			driver = new FirefoxDriver();
		} else {
			DesiredCapabilities capabillities = null;
			String browser = Config.getBrowser();
			if (browser.equalsIgnoreCase("firefox")) {
				capabillities = DesiredCapabilities.firefox();
			} else if (browser.equalsIgnoreCase("ie")) {
				capabillities = DesiredCapabilities.internetExplorer();
			} else if (browser.equalsIgnoreCase("chrome")) {
				 capabillities = DesiredCapabilities.chrome();
			}
			capabillities.setCapability("version", Config.getBrowserVersion());
			capabillities.setCapability("platform", Config.getBrowserOS());
			capabillities.setCapability("name", Config.getTestDescription());
			capabillities.setCapability("public", true);
			capabillities.setCapability("restricted-public-info", true);
			
			driver = new RemoteWebDriver(
					new URL("http://"+Config.getSauceAccount()+":"+Config.getSauceKey() + "@" +
							Config.getSeleniumServer()), capabillities);
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

}
