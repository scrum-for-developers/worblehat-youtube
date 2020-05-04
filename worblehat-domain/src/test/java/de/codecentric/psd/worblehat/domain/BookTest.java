package de.codecentric.psd.worblehat.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest {

  private Book book;

  @BeforeEach
  void setup() {
    book = new Book("Titel", "Author", "2", "1", 1234);
  }

  @Test
  void shouldCreateCompleteObjectFromParameter() {
    Book b = new Book(new BookParameter("Title", "Author", "2", "1", 1234, "Description"));
    assertThat(b.getTitle(), is("Title"));
    assertThat(b.getAuthor(), is("Author"));
    assertThat(b.getEdition(), is("2"));
    assertThat(b.getIsbn(), is("1"));
    assertThat(b.getYearOfPublication(), is(1234));
    assertThat(b.getDescription(), is("Description"));
  }

  @Test
  void shouldNotShallowCopy() {
    book.borrowNowByBorrower("andreas");
    Book anotherCopy = new Book(book);
    assertThat(book.getBorrowing(), not(sameInstance(anotherCopy.getBorrowing())));
  }

  @Test
  void shouldReturnFalseWhenAuthorisDifferent() {
    Book anotherCopy = new Book(book);
    anotherCopy.setAuthor("Bene");
    assertThat(book.isSameCopy(anotherCopy), is(false));
  }

  @Test
  void shouldReturnFalseWhenTitleisDifferent() {
    Book anotherCopy = new Book(book);
    anotherCopy.setTitle("Lord of the Rings");
    assertThat(book.isSameCopy(anotherCopy), is(false));
  }

  @Test
  void shouldReturnFalseWhenEditionIsDifferent() {
    Book anotherCopy = new Book(book);
    anotherCopy.setEdition("3");
    assertThat(book.isSameCopy(anotherCopy), is(false));
  }

  @Test
  void shouldReturnTrueWhenAllButTitleAndAuthorAndEditionAreDifferent() {
    Book anotherCopy = new Book(book);
    anotherCopy.setIsbn("123456789X");
    anotherCopy.setYearOfPublication(2010);
    assertThat(book.isSameCopy(anotherCopy), is(true));
  }

  @Test
  void shouldBeBorrowable() {
    book.borrowNowByBorrower("a@bc.de");
    assertThat(book.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
  }

  @Test
  void shouldIgnoreNewBorrowWhenBorrowed() {
    book.borrowNowByBorrower("a@bc.de");
    book.borrowNowByBorrower("a@bc.ru");
    assertThat(book.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
  }
}
