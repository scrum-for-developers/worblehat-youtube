package de.codecentric.psd.worblehat.acceptancetests.step.business;

import com.google.common.base.Splitter;
import de.codecentric.psd.worblehat.acceptancetests.step.StoryContext;
import de.codecentric.psd.worblehat.domain.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class Library {

  @Autowired(required = true)
  private BookService bookService;

  @Autowired private BorrowingRepository borrowingRepository;

  @Autowired private BookRepository bookRepository;

  private StoryContext storyContext;

  @Autowired
  public Library(ApplicationContext applicationContext, StoryContext storyContext) {
    this.bookService = applicationContext.getBean(BookService.class);
    this.storyContext = storyContext;
  }

  // *******************
  // *** G I V E N *****
  // *******************

  @Given("an empty library")
  public void emptyLibrary() {
    bookService.deleteAllBooks();
  }

  @Given("a library, containing only one book with isbn {string}")
  public void createLibraryWithSingleBookWithGivenIsbn(String isbn) {
    emptyLibrary();
    Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
    Optional<Book> createdBook =
        bookService.createBook(
            new BookParameter(
                book.getTitle(),
                book.getAuthor(),
                book.getEdition(),
                book.getIsbn(),
                book.getYearOfPublication(),
                book.getDescription()));
    createdBook.ifPresent(b -> storyContext.putObject("LAST_INSERTED_BOOK", b));
  }

  @Given("{string} has borrowed book(s) {string}")
  public void borrowerHasBorrowerdBooks(String borrower, String isbns) {
    has_borrowed_books_on(borrower, isbns, LocalDate.now());
  }

  @Given("{string} has borrowed books {string} on {date}")
  public void has_borrowed_books_on(String borrower, String isbns, LocalDate borrowDate) {
    Splitter.on(" ").omitEmptyStrings().splitToList(isbns).stream()
        .forEach(
            isbn -> {
              Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
              Book newBook =
                  bookService
                      .createBook(
                          new BookParameter(
                              book.getTitle(),
                              book.getAuthor(),
                              book.getEdition(),
                              book.getIsbn(),
                              book.getYearOfPublication(),
                              book.getDescription()))
                      .orElseThrow(IllegalStateException::new);

              Date date = Date.from(borrowDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

              Borrowing newBorrowing = new Borrowing(newBook, borrower, date);
              borrowingRepository.save(newBorrowing);

              newBook.setBorrowing(newBorrowing);
              bookRepository.save(newBook);
            });
  }

  // *****************
  // *** W H E N *****
  // *****************

  // *****************
  // *** T H E N *****
  // *****************

  @Then("the library contains {long} books")
  public void shouldContainNumberOfBooks(long books) {
    assertThat(bookRepository.count(), is(books));
  }

    @Then("the library contains {int} book(s) with {string}")
    public void shouldContainNumberOfBooksWithISBN(int books, String isbn) {
        if (isbn.isEmpty()) return;
        assertThat(bookRepository.findByIsbn(isbn).size(), is(books));
    }

    @Then("the library still contains all borrowed books {string}")
    public void shouldContainOneCopyOf(String isbn) {
        if (isbn.isEmpty()) return;
        assertThat(bookRepository.findByIsbn(isbn).size(), is(1));
    }

    @Then("the new book {string} be added")
  public void shouldNotHaveCreatedANewCopy(String can) {
    Book lastInsertedBook = (Book) storyContext.getObject("LAST_INSERTED_BOOK");
    int numberOfCopies = "CAN".equals(can) ? 2 : 1;
    assertNumberOfCopies(lastInsertedBook.getIsbn(), numberOfCopies);
  }

  private void assertNumberOfCopies(String isbn, int nrOfCopies) {
    Set<Book> books = bookService.findBooksByIsbn(isbn);
    assertThat(books.size(), is(nrOfCopies));
    assertThat(books, everyItem(hasProperty("isbn", is(isbn))));
  }
}
