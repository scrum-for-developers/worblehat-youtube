package de.codecentric.psd.worblehat.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BookTest {

    Book BOOK;

        @BeforeEach
        void setup() {
        //TODO: Book.fromParameter
        BOOK = new Book("Titel", "Author", "2", "1", 1234);
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
    void shouldReturnFalseWhenAuthorisDifferent() {
        Book anotherCopy = new Book(BOOK);
        anotherCopy.setAuthor("Bene");
        assertThat(BOOK.isSameCopy(anotherCopy), is(false));
    }

    @Test
    void shouldReturnFalseWhenTitleisDifferent() {
        Book anotherCopy = new Book(BOOK);
        anotherCopy.setTitle("Lord of the Rings");
        assertThat(BOOK.isSameCopy(anotherCopy), is(false));
    }

    @Test
    void shouldReturnTrueWhenAllButTitleAndAuthorAreDifferent() {
        Book anotherCopy = new Book(BOOK);
        anotherCopy.setEdition("2000");
        anotherCopy.setIsbn("123456789X");
        anotherCopy.setYearOfPublication(2010);
        assertThat(BOOK.isSameCopy(anotherCopy), is(true));
    }

    @Test
    void shouldBeBorrowable() {
        BOOK.borrowNowByBorrower("a@bc.de");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }

    @Test
    void shouldIgnoreNewBorrowWhenBorrowed() {
        BOOK.borrowNowByBorrower("a@bc.de");
        BOOK.borrowNowByBorrower("a@bc.ru");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }
}
