package de.codecentric.psd.atdd.step.page;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import de.codecentric.psd.atdd.adapter.Config;
import de.codecentric.psd.atdd.adapter.SeleniumAdapter;

public class ReturnAllBooks {
	private final WebDriver driver;
	private final SeleniumAdapter selenium;

	@Inject
	public ReturnAllBooks(SeleniumAdapter selenium) {
		this.selenium = selenium;
		driver = selenium.getDriver();
	}
	
	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************
	
	@When("user <user> returns all his books")
	public void whenUseruserReturnsAllHisBooks(@Named("user") String user) throws InterruptedException{
		openReturnAllBooksPage();
		typeIntoField("emailAddress", user);
		submitForm();
		selenium.waitUntilPageContainsId("welcome_heading");
	}

	// *****************
	// *** U T I L ***** 
	// *****************

	private void submitForm() {
		driver.findElement(By.id("returnAllBooks")).click();
	}

	private void openReturnAllBooksPage() {
		driver.get(Config.getApplicationURL() + "/"
		+ Config.getApplicationContext() + "/returnAllBooks");
	}
	
	private void typeIntoField(String id, String value) {
		WebElement element = driver.findElement(By.id(id));
		element.clear();
		element.sendKeys(value);
	}	
}
