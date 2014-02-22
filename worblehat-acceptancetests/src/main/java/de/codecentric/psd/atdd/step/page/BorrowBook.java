package de.codecentric.psd.atdd.step.page;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import de.codecentric.psd.atdd.library.Config;
import de.codecentric.psd.atdd.library.SeleniumAdapter;

public class BorrowBook {
	
	private WebDriver driver;

	@Inject
	public BorrowBook(SeleniumAdapter selenium) {
		driver = selenium.getDriver();
	}
	
	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************
	
	@When("user <user> borrows the book <isbn>")
	public void whenUseruserBorrowsTheBookisbn(@Named("user") String user, @Named("isbn") String isbn){
		openBorrowBookPage();
		typeIntoField("email", user);
		typeIntoField("isbn", isbn);
		submitForm();
	}
	
	// *****************
	// *** T H E N *****
	// *****************
	
	// *****************
	// *** U T I L ***** 
	// *****************

	private void submitForm() {
		driver.findElement(By.id("borrowBook")).click();
	}

	private void openBorrowBookPage() {
		driver.get(Config.getApplicationURL() + "/"
				+ Config.getApplicationContext() + "/borrow");
	}
	
	private void typeIntoField(String id, String value) {
		WebElement element = driver.findElement(By.id(id));
		element.clear();
		element.sendKeys(value);
	}

}
