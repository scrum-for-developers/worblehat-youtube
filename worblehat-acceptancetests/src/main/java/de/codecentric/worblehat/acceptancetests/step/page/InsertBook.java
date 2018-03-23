package de.codecentric.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsertBook {

	private SeleniumAdapter seleniumAdapter;

	@Autowired
	public InsertBook(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}

	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****
	// *****************

	// TODO: write story that contains whitespaces in examples table

	@When("a librarian adds a book with title $title, author $author, edition $edition, year $year and isbn $isbn")
	public void whenABookWithISBNisbnIsAdded(String title,
											 String author,
											 String edition,
											 String year,
											 String isbn) {
		seleniumAdapter.gotoPage(Page.INSERTBOOKS);
		fillInsertBookForm(title, author, edition, isbn, year);
		seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
	}

	// *****************
	// *** T H E N *****
	// *****************

	@Then("the page contains error message $message")
	public void pageContainsErrorMessage(String message){
		List<String> errorMsgs = seleniumAdapter.findAllStringsForElement(PageElement.ERROR);
		assertThat(errorMsgs, contains(message));
	}

	// *****************
	// *** U T I L ***** 
	// *****************


	private void fillInsertBookForm(String title, String author, String edition, String isbn,
			 String year) {
		seleniumAdapter.typeIntoField("title", title);
		seleniumAdapter.typeIntoField("edition", edition);
		seleniumAdapter.typeIntoField("isbn", isbn);
		seleniumAdapter.typeIntoField("author", author);
		seleniumAdapter.typeIntoField("yearOfPublication", year);
	}


}
