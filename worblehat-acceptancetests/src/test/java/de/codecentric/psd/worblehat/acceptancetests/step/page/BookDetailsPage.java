package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class BookDetailsPage {

  private SeleniumAdapter seleniumAdapter;
  private StoryContext storyContext;
  private BookService bookService;

  @Autowired
  public BookDetailsPage(
      SeleniumAdapter seleniumAdapter, StoryContext storyContext, BookService bookService) {
    this.seleniumAdapter = seleniumAdapter;
    this.storyContext = storyContext;
    this.bookService = bookService;
  }

  @When("I navigate to the detail page of the book with the isbn {string}")
  public void navigateToDetailPage(String isbn) {
    seleniumAdapter.clickOnPageElementByClassName("detailsLink-" + isbn);
    storyContext.put("LAST_BROWSED_BOOK_DETAILS", isbn);
  }

  @Then("I can see all book details for that book")
  public void allBookDetailsVisible() {
    String isbn = storyContext.get("LAST_BROWSED_BOOK_DETAILS");
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    assertThat(books, hasSize(greaterThanOrEqualTo(1)));
    Book book = books.iterator().next();
    String description = book.getDescription();
    assertThat(seleniumAdapter.containsTextOnPage(book.getIsbn()), is(true));
    assertThat(seleniumAdapter.containsTextOnPage(book.getAuthor()), is(true));
    assertThat(seleniumAdapter.containsTextOnPage(book.getTitle()), is(true));
    assertThat(seleniumAdapter.containsTextOnPage(book.getEdition()), is(true));
    assertThat(
        seleniumAdapter.containsTextOnPage(String.valueOf(book.getYearOfPublication())), is(true));
    Optional.ofNullable(description)
        .ifPresent(desc -> assertThat(seleniumAdapter.containsTextOnPage(description), is(true)));
  }
}
