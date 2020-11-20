package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

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

  @When("{string} borrows the book {string}")
  public void whenUseruserBorrowsTheBookisbn(String user, String isbn) {
    seleniumAdapter.gotoPage(Page.BORROWBOOK);
    seleniumAdapter.typeIntoField("email", user);
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.clickOnPageElementById(PageElement.BORROW_BOOK_BUTTON);
  }

  // *****************
  // *** T H E N *****
  // *****************

  @Then(
      "there's an error {string}, when {string} tries to borrow the book with isbn {string} again")
  public void whenBorrowerBorrowsBorrowedBookShowErrorMessage(
      String message, String borrower, String isbn) {
    seleniumAdapter.gotoPage(Page.BORROWBOOK);
    seleniumAdapter.typeIntoField("email", borrower);
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.clickOnPageElementById(PageElement.BORROW_BOOK_BUTTON);
    String errorMessage = seleniumAdapter.getTextFromElement(PageElement.ISBN_ERROR);
    assertThat(errorMessage, is(message));
  }
}
