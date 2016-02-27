package de.codecentric.psd.atdd.step.page;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import de.codecentric.psd.atdd.adapter.wrapper.Page;
import de.codecentric.psd.atdd.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

import de.codecentric.psd.atdd.adapter.Config;
import de.codecentric.psd.atdd.adapter.SeleniumAdapter;
import org.springframework.beans.factory.annotation.Autowired;

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

	@When("a librarian adds a book with title <title>, author <author>, edition <edition>, year <year> and isbn <isbn>")
	public void whenABookWithISBNisbnIsAdded(@Named("title") String title,
											 @Named("author")String author,
											 @Named("edition") String edition,
											 @Named("year") String year,
											 @Named("isbn") String isbn) {
		seleniumAdapter.gotoPage(Page.INSERTBOOKS);
		fillInsertBookForm(title, author, edition, isbn, year);
		seleniumAdapter.clickOnPageElement(PageElement.ADDBOOKBUTTON);
	}

	// *****************
	// *** T H E N *****
	// *****************


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
