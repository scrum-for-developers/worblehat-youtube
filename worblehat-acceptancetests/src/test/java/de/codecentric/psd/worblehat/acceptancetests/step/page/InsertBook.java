package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.Book;
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

  @When(
      "a librarian adds a book with title {string}, author {string}, edition {string}, year {string} and isbn {string}")
  public void whenABookWithISBNisbnIsAdded(
      String title, String author, String edition, String year, String isbn) {
    insertAndSubmitBook(title, author, edition, year, "", isbn);
  }

  @When(
      "a librarian adds a book with title {string}, author {string}, edition {string}, year {string}, description {string} and isbn {string}")
  public void whenABookIsAdded(
      String title, String author, String edition, String year, String description, String isbn) {
    insertAndSubmitBook(title, author, edition, year, description, isbn);
  }

  @When("a librarian tries to add a similar book with different {string}, {string} and {string}")
  public void whenASimilarBookIsAdded(String title, String author, String edition) {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    insertAndSubmitBook(
        title,
        author,
        edition,
        String.valueOf(lastInsertedBook.getYearOfPublication()),
        lastInsertedBook.getDescription(),
        lastInsertedBook.getIsbn());
  }

  @When("a librarian tries to add a similar book with same title, author and edition")
  public void whenASimilarBookIsAdded() {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    insertAndSubmitBook(
        lastInsertedBook.getTitle(),
        lastInsertedBook.getAuthor(),
        lastInsertedBook.getEdition(),
        String.valueOf(lastInsertedBook.getYearOfPublication()),
        lastInsertedBook.getDescription(),
        lastInsertedBook.getIsbn());
  }

  // *****************
  // *** T H E N *****
  // *****************
  @Then("the page contains error message for field {string}")
  public void pageContainsErrorMessage(String field) {
    String errorMessage =
        seleniumAdapter.getTextFromElement(
            ("isbn".equals(field) ? PageElement.ISBN_ERROR : PageElement.EDITION_ERROR));
    assertThat(errorMessage, notNullValue());
  }

  // *****************
  // *** U T I L *****
  // *****************

  private void insertAndSubmitBook(
      String title, String author, String edition, String year, String description, String isbn) {
    seleniumAdapter.gotoPage(Page.INSERTBOOKS);
    fillInsertBookForm(title, author, edition, isbn, year, description);
    seleniumAdapter.clickOnPageElementById(PageElement.ADDBOOKBUTTON);
  }

  private void fillInsertBookForm(
      String title, String author, String edition, String isbn, String year, String description) {
    seleniumAdapter.typeIntoField("title", title);
    seleniumAdapter.typeIntoField("edition", edition);
    seleniumAdapter.typeIntoField("isbn", isbn);
    seleniumAdapter.typeIntoField("author", author);
    seleniumAdapter.typeIntoField("yearOfPublication", year);
    Optional.ofNullable(description)
        .ifPresent(desc -> seleniumAdapter.typeIntoField("description", description));
  }
}
