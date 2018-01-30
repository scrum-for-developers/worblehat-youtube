package de.codecentric.psd.worblehat.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookTest {

    Book BOOK;

    @Before
    public void setup() {
        BOOK = new Book("Titel", "Author", "2", "1", 1234);
    }

    @Test
    public void shouldReturnFalseForNull() throws Exception {
        assertThat(BOOK.isSameCopy(Optional.empty()), is(false));
    }

    @Test
    public void shouldBeBorrowable() throws Exception {
        BOOK.borrowNowByBorrower("a@bc.de");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }

    @Test
    public void shouldIgnoreNewBorrowWhenBorrowed() throws Exception {
        BOOK.borrowNowByBorrower("a@bc.de");
        BOOK.borrowNowByBorrower("a@bc.ru");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }
}