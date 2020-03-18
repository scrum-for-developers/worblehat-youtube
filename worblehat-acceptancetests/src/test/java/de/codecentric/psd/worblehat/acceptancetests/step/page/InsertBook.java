package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookParameter;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class InsertBook {

  private SeleniumAdapter seleniumAdapter;

  @Autowired public StoryContext storyContext;

  @Autowired
  public InsertBook(SeleniumAdapter seleniumAdapter, StoryContext storyContext) {
    this.seleniumAdapter = seleniumAdapter;
    this.storyContext = storyContext;
  }

  // *******************
  // *** G I V E N *****
  // *******************

  // *****************
  // *** W H E N *****
  // *****************

  @When("a librarian adds a book with {string}, {string}, {string}, {int}, and {string}")
  public void whenABookWithISBNisbnIsAdded(
      String isbn, String title, String author, Integer edition, String year) {
    insertAndSubmitBook(isbn, title, author, edition, year, "");
  }
  @When("a librarian adds a book with {string}, {string}, {string}, {int}, {string}, and {string}")
  public void whenABookIsAdded(
      String isbn, String title, String author, Integer edition, String year, String description) {
    insertAndSubmitBook(isbn, title, author, edition, year, description);
  }

  @When("a librarian tries to add a similar book with different {string}, {string} and {int}")
  public void whenASimilarBookIsAdded(String title, String author, Integer edition) {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    insertAndSubmitBook(
        lastInsertedBook.getIsbn(),
        title,
        author,
        edition,
        String.valueOf(lastInsertedBook.getYearOfPublication()),
        lastInsertedBook.getDescription()
        );
  }

  @When("a librarian tries to add a similar book with same title, author and edition")
  public void whenASimilarBookIsAdded() {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    insertAndSubmitBook(
        lastInsertedBook.getIsbn(),
        lastInsertedBook.getTitle(),
        lastInsertedBook.getAuthor(),
        Integer.parseInt(lastInsertedBook.getEdition()),
        String.valueOf(lastInsertedBook.getYearOfPublication()),
        lastInsertedBook.getDescription()
        );
  }

  // *****************
  // *** T H E N *****
  // *****************
  @Then("the page contains error message for field {string}")
  public void pageContainsErrorMessage(String field) {
    String errorMessage = seleniumAdapter.getTextFromElement(PageElement.errorFor(field));
    assertThat(errorMessage, notNullValue());
  }

  // *****************
  // *** U T I L *****
  // *****************

  private void insertAndSubmitBook(
      String isbn, String title, String author, Integer edition, String year, String description) {
    seleniumAdapter.gotoPage(Page.INSERTBOOKS);
    fillInsertBookForm(title, author, edition, isbn, year, description);
    seleniumAdapter.clickOnPageElementById(PageElement.ADDBOOKBUTTON);
    storyContext.putObject("LAST_INSERTED_BOOK", new Book(new BookParameter(title, author, edition.toString(), isbn, Integer.parseInt(year.trim()), description)));
  }

  private void fillInsertBookForm(
      String title, String author, Integer edition, String isbn, String year, String description) {
    seleniumAdapter.typeIntoField("title", title);
    seleniumAdapter.typeIntoField("edition", edition.toString());
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.typeIntoField("author", author);
    seleniumAdapter.typeIntoField("yearOfPublication", year);
    Optional.ofNullable(description)
        .ifPresent(desc -> seleniumAdapter.typeIntoField("description", description));
  }
}
