package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.business.DemoBookFactory;
import de.codecentric.psd.worblehat.domain.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BookList {

  private SeleniumAdapter seleniumAdapter;

  @Autowired
  public BookList(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  @Given("I browse the list of all books")
  public void browseBookList() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
  }

  @Then(
      "the booklist contains a book with values title {string}, author {string}, year {string}, edition {string}, isbn {string}")
  public void bookListContainsRowWithValues(
      final String title,
      final String author,
      final String year,
      final String edition,
      final String isbn) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    assertThat(title, is(htmlBook.getTitle()));
    assertThat(author, is(htmlBook.getAuthor()));
    assertThat(year, is(htmlBook.getYearOfPublication()));
    assertThat(edition, is(htmlBook.getEdition()));
    assertThat(isbn, is(htmlBook.getIsbn()));
  }

  @Then(
      "the booklist contains a book with values title {string}, author {string}, year {string}, edition {string}, isbn {string} and description {string}")
  public void bookListContainsRowWithValues(
      final String title,
      final String author,
      final String year,
      final String edition,
      final String isbn,
      final String description) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn.trim());
    assertThat(title, containsString(htmlBook.getTitle()));
    assertThat(author, containsString(htmlBook.getAuthor()));
    assertThat(year, containsString(htmlBook.getYearOfPublication()));
    assertThat(edition, containsString(htmlBook.getEdition()));
    assertThat(isbn, containsString(htmlBook.getIsbn()));
    assertThat(description, containsString(htmlBook.getDescription()));
  }

  @Then("The library contains no books")
  public void libraryIsEmpty() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    assertThat(htmlBookList.size(), is(0));
  }

  @Then("the booklist lists the user {string} as borrower for the book with isbn {string}")
  public void bookListHasBorrowerForBookWithIsbn(final String borrower, final String isbn) {
    Book book = DemoBookFactory.createDemoBook().build();
    Map<String, String> wantedRow =
        createRowMap(
            book.getTitle(),
            book.getAuthor(),
            String.valueOf(book.getYearOfPublication()),
            book.getEdition(),
            isbn,
            borrower);
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    assertThat(htmlBook.getBorrower(), is(borrower));
  }

  @Then("books {string} are {string} by borrower {string}")
  public void booksAreNotBorrowedByBorrower1(String isbns, String borrowStatus, String borrower) {
    boolean shouldNotBeBorrowed = borrowStatus.contains("not borrowed");

    List<String> isbnList = getListOfItems(isbns);
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    for (String isbn : isbnList) {
      String actualBorrower = htmlBookList.getBookByIsbn(isbn).getBorrower();
      if (shouldNotBeBorrowed) {
        assertThat(actualBorrower, is(isEmptyOrNullString()));
      } else {
        assertThat(actualBorrower, is(borrower));
      }
    }
  }

  @Then("for every book the booklist contains a cover")
  public void checkCover() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
    Collection<HtmlBook> books = htmlBookList.getHtmlBooks().values();
    for (HtmlBook book : books) {
      assertThat(book.getCover(), containsString(book.getIsbn()));
    }
  }

  private List<String> getListOfItems(String isbns) {
    return isbns.isEmpty() ? Collections.emptyList() : Arrays.asList(isbns.split(" "));
  }

  private HashMap<String, String> createRowMap(
      final String title,
      final String author,
      final String year,
      final String edition,
      final String isbn,
      final String borrower) {
    return new HashMap<String, String>() {
      {
        put("Title", title);
        put("Author", author);
        put("Year", year);
        put("Edition", edition);
        put("ISBN", isbn);
        put("Borrower", borrower);
      }
    };
  }
}
