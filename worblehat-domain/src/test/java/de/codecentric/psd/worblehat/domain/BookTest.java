package de.codecentric.psd.worblehat.domain;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BookTest {

    private static final String TITLE = "title";

    private static final String AUTHOR = "author";

    private static final String EDITION = "2";

    private static final String ISBN = "isbn";

    private static final int YEAR_OF_PUBLICATION = 2016;
    private Book demoBook;

    private Book createDemoBook() {
        return new Book(TITLE, AUTHOR, EDITION, ISBN, YEAR_OF_PUBLICATION);
    }

    @Before
    public void setUp() throws Exception {
        demoBook = createDemoBook();
    }

    @Test
    public void testConstructorTakesAllArguments() throws Exception {
        assertNotNull(demoBook);
    }

    @Test
    public void testBookHasTitle() throws Exception {
        String title = demoBook.getTitle();
        assertThat(title, is(TITLE));
    }

    @Test
    public void testBookHasAuthor() throws Exception {
        String author = demoBook.getAuthor();
        assertThat(author, is(AUTHOR));
    }

    @Test
    public void testBookHasEdition() throws Exception {
        String edition = demoBook.getEdition();
        assertThat(edition, is(EDITION));
    }

    @Test
    public void testBookHasISBN() throws Exception {
        String edition = demoBook.getIsbn();
        assertThat(edition, is(ISBN));
    }

    @Test
    public void testBookHasYearOfPublication() throws Exception {
        int yearOfPublication = demoBook.getYearOfPublication();
        assertThat(yearOfPublication, is(YEAR_OF_PUBLICATION));
    }

    @Test
    public void shouldReturnEmptyStringIfBorrowerIsNull() throws Exception {
        String borrower = demoBook.getBorrowerEmail();
        assertThat(borrower, is(""));
    }

    @Test
    public void shouldReturnBorrowerIsBorrowerIsSet() throws Exception {
        String expectedBorrower = "someone@codecentric.de";
        Borrowing borrowing = new Borrowing(demoBook, expectedBorrower, DateTime.now());
        demoBook.setBorrowing(borrowing);
        String actualBorrower = demoBook.getBorrowerEmail();
        assertThat(actualBorrower, is(expectedBorrower));
    }

    @Test
    public void shouldTakeTitle() throws Exception {
        String expectedTitle = "A new Title";
        demoBook.setTitle(expectedTitle);
        String actualTitle = demoBook.getTitle();
        assertThat(actualTitle, is(expectedTitle));
    }

    @Test
    public void shouldTakeAuthor() throws Exception {
        String expectedAuthor = "Someone I knew";
        demoBook.setAuthor(expectedAuthor);
        String actualAuthor = demoBook.getAuthor();
        assertThat(actualAuthor, is(expectedAuthor));
    }

    @Test
    public void shouldTakeISBN() throws Exception {
        String expectedISBN = "12354545";
        demoBook.setIsbn(expectedISBN);
        String actualISBN = demoBook.getIsbn();
        assertThat(actualISBN, is(expectedISBN));
    }

    @Test
    public void shouldTakeEdition() throws Exception {
        String expectedEdition = "1";
        demoBook.setEdition(expectedEdition);
        String actualEdition = demoBook.getEdition();
        assertThat(actualEdition, is(expectedEdition));
    }

    @Test
    public void shouldTakeYearOfPublication() throws Exception {
        int expectedYearOfPublication = 2016;
        demoBook.setYearOfPublication(expectedYearOfPublication);
        int actualYearOfPublication = demoBook.getYearOfPublication();
        assertThat(actualYearOfPublication, is(expectedYearOfPublication));
    }
}
