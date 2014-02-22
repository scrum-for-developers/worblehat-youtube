package de.codecentric.psd.atdd.step.page;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.Inject;

import de.codecentric.psd.atdd.library.Config;
import de.codecentric.psd.atdd.library.SeleniumAdapter;

public class InsertBook {

	private WebDriver driver;

	@Inject
	public InsertBook(SeleniumAdapter selenium) {
		driver = selenium.getDriver();
	}

	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************

	@When("a book with ISBN <isbn> is added")
	public void whenABookWithISBNisbnIsAdded(@Named("isbn") String isbn) {
		openInsertBooksPage();
		fillInsertBookForm("Title", "2", isbn, "Author", "2002");
		submitForm();
	}

	@When("the librarian tries to add a book with an <attribute> of <value>")
	public void addABook(@Named("attribute") String attribute,
			@Named("value") String value) {
		openInsertBooksPage();
		fillInsertBookForm("Title", "1", "123456789X", "Author", "2002");
		typeIntoField(getIdForAttribute(attribute), value);
		submitForm();
	}

	// *****************
	// *** T H E N *****
	// *****************


	@Then("the page contains error message <message>")
	public void thenThePageContainsErrorMessagemessage(@Named("message") String message){
		assertThat(driver.getPageSource(), containsString(message));
	}

	// *****************
	// *** U T I L ***** 
	// *****************


	private void setTitle(String titel) {
		typeIntoField("title", titel);
	}

	private void setEdition(String edition) {
		typeIntoField("edition", edition);
	}

	private void setYear(String year) {
		typeIntoField("year", year);
	}

	private void setAuthor(String author) {
		typeIntoField("author", author);
	}

	private void setIsbn(String isbn) {
		typeIntoField("isbn", isbn);
	}

	private void typeIntoField(String id, String value) {
		WebElement element = driver.findElement(By.id(id));
		element.clear();
		element.sendKeys(value);
	}

	private void fillInsertBookForm(String titel, String edition, String isbn,
			String author, String year) {
		setTitle(titel);
		setEdition(edition);
		setIsbn(isbn);
		setAuthor(author);
		setYear(year);
	}

	private void submitForm() {
		driver.findElement(By.id("addBook")).click();
	}

	private void openInsertBooksPage() {
		driver.get(Config.getApplicationURL() + "/"
				+ Config.getApplicationContext() + "/insertBooks");
	}

	private String getIdForAttribute(String attribute) {
		if (attribute.equals("ISBN"))
			return "isbn";
		if (attribute.equals("Author"))
			return "author";
		if (attribute.equals("Edition"))
			return "edition";
		return null;
	}

}
