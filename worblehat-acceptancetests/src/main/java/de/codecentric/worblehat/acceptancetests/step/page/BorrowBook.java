package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
public class BorrowBook {
	
	private SeleniumAdapter seleniumAdapter;

	@Autowired
	public BorrowBook(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}
	
	// *******************
	// *** G I V E N *****
	// *******************

	// *****************
	// *** W H E N *****s
	// *****************
	
	@When("user <borrower> borrows the book <isbn>")
	public void whenUseruserBorrowsTheBookisbn(@Named("borrower") String user, @Named("isbn") String isbn){
		seleniumAdapter.gotoPage(Page.BORROWBOOK);
		seleniumAdapter.typeIntoField("email", user);
		seleniumAdapter.typeIntoField("isbn", isbn);
		seleniumAdapter.clickOnPageElement(PageElement.BORROWBOOKBUTTON);
	}
	
	// *****************
	// *** T H E N *****
	// *****************

	@Then("I get an error message <message> when the borrower <borrower> tries to borrow the book with isbn <isbn> again")
	public void whenBorrowerBorrowsBorrowedBookShowErrorMessage(@Named("borrower")String borrower,
																@Named("isbn")String isbn,
																@Named("message")String message){
		seleniumAdapter.gotoPage(Page.BORROWBOOK);
		seleniumAdapter.typeIntoField("email", borrower);
		seleniumAdapter.typeIntoField("isbn", isbn);
		seleniumAdapter.clickOnPageElement(PageElement.BORROWBOOKBUTTON);
		String errorMessage = seleniumAdapter.getTextFromElement(PageElement.ISBNERROR);
		assertThat(errorMessage, is(message));
	}


}
