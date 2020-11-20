package de.codecentric.psd.worblehat.acceptancetests.step.page;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.google.common.base.Splitter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BookList {

  private SeleniumAdapter seleniumAdapter;

  @Autowired public StoryContext storyContext;

  @Autowired
  public BookList(SeleniumAdapter seleniumAdapter) {
    this.seleniumAdapter = seleniumAdapter;
  }

  @Given("I browse the list of all books")
  public void browseBookList() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
  }

  @Then("the booklist contains a book with {string}, {string}, {string}, {int} and {string}")
  public void bookListContainsRowWithValues(
      final String title,
      final String author,
      final String year,
      final Integer edition,
      final String isbn) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    assertThat(title, is(htmlBook.getTitle()));
    assertThat(author, is(htmlBook.getAuthor()));
    assertThat(year, is(htmlBook.getYearOfPublication()));
    assertThat(edition, is(htmlBook.getEdition()));
    assertThat(isbn, is(htmlBook.getIsbn()));
  }

  @Then("the booklist contains a book with all the properties from the last inserted book")
  public void justAsLastInserted() {
    Book lastInserted = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(lastInserted.getIsbn());
    assertThat(lastInserted.getTitle(), containsString(htmlBook.getTitle()));
    assertThat(lastInserted.getAuthor(), containsString(htmlBook.getAuthor()));
    assertThat(
        lastInserted.getYearOfPublication(), is(Integer.parseInt(htmlBook.getYearOfPublication())));
    assertThat(lastInserted.getEdition(), containsString(htmlBook.getEdition().toString()));
    assertThat(lastInserted.getDescription(), containsString(htmlBook.getDescription()));
  }

  @Then(
      "the booklist contains a book with {string}, {string}, {string}, {int}, {string}, and {string}")
  public void bookListContainsRowWithValues(
      final String isbn,
      final String title,
      final String author,
      final Integer edition,
      final String year,
      final String description) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn.trim());
    assertThat(title, containsString(htmlBook.getTitle()));
    assertThat(author, containsString(htmlBook.getAuthor()));
    assertThat(year, containsString(htmlBook.getYearOfPublication()));
    assertThat(edition, is(htmlBook.getEdition()));
    assertThat(isbn, containsString(htmlBook.getIsbn()));
    assertThat(description, containsString(htmlBook.getDescription()));
  }

  @Then("The library contains no books")
  public void libraryIsEmpty() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    assertThat(htmlBookList.size(), is(0));
  }

  @Then("the booklist lists {string} as borrower for the book with isbn {string}")
  public void bookListHasBorrowerForBookWithIsbn(final String borrower, final String isbn) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
    assertThat(htmlBook.getBorrower(), is(borrower));
  }

  @Then("book(s) {string} is/are {string} by {string}")
  public void booksAreNotBorrowedByBorrower1(String isbns, String borrowStatus, String borrower) {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);

    long booksInWrongBorrowingState =
        Splitter.on(" ").omitEmptyStrings().splitToList(isbns).stream()
            .filter(
                isbn -> {
                  String actualBorrower = htmlBookList.getBookByIsbn(isbn).getBorrower();
                  return !borrowStatus.contains("not borrowed") != borrower.equals(actualBorrower);
                })
            .count();

    assertThat(booksInWrongBorrowingState, is(0L));
  }

  @Then("for every book the booklist contains a cover")
  public void checkCover() {
    seleniumAdapter.gotoPage(Page.BOOKLIST);
    HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOK_LIST);
    Collection<HtmlBook> books = htmlBookList.getHtmlBooks().values();
    for (HtmlBook book : books) {
      assertThat(book.getCover(), containsString(book.getIsbn()));
    }
  }
}
