package de.codecentric.psd.worblehat.domain;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BorrowingTest {

    private final static String BORROWER_EMAIL = "someone@codecentric.de";

    private final static DateTime BORROW_DATE = DateTime.now();

    private final static Book BORROW_BOOK =
            new Book("title", "author", "edition", "ISBN", 2016);

    private Borrowing createBorrowing() {
        return new Borrowing(BORROW_BOOK, BORROWER_EMAIL, BORROW_DATE);
    }

    @Test
    public void testConstructorShouldTakeAllArguments() throws Exception {
        Borrowing borrowing = createBorrowing();
        assertNotNull(borrowing);
    }

    @Test
    public void testHasBorrowerEmail() throws Exception {
        Borrowing borrowing = createBorrowing();
        String borrowerEmail = borrowing.getBorrowerEmailAddress();
        assertThat(borrowerEmail, is(BORROWER_EMAIL));
    }


}