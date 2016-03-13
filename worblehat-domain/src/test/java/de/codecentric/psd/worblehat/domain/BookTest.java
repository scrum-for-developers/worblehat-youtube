package de.codecentric.psd.worblehat.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BookTest {

    private static final String TITLE = "title";

    private static final String AUTHOR = "author";

    private static final String EDITION = "2";

    private static final String ISBN = "isbn";

    private static final int YEAR_OF_PUBLICATION = 2016;

    private Book createDemoBook() {
        return new Book(TITLE, AUTHOR, EDITION, ISBN, YEAR_OF_PUBLICATION);
    }



    @Test
    public void testConstructorTakesAllArguments() throws Exception {
        Book demoBook = createDemoBook();
        assertNotNull(demoBook);
    }

    @Test
    public void testBookHasTitle() throws Exception {
        Book demoBook = createDemoBook();
        String title = demoBook.getTitle();
        assertThat(title, is(TITLE));
    }

    @Test
    public void testBookHasAuthor() throws Exception {
        Book demoBook = createDemoBook();
        String author = demoBook.getAuthor();
        assertThat(author, is(AUTHOR));
    }

    @Test
    public void testBookHasEdition() throws Exception {
        Book demoBook = createDemoBook();
        String edition = demoBook.getEdition();
        assertThat(edition, is(EDITION));
    }

    @Test
    public void testBookHasISBN() throws Exception {
        Book demoBook = createDemoBook();
        String edition = demoBook.getIsbn();
        assertThat(edition, is(ISBN));
    }

    @Test
    public void testBookHasYearOfPublication() throws Exception {
        Book demoBook = createDemoBook();
        int yearOfPublication = demoBook.getYearOfPublication();
        assertThat(yearOfPublication, is(YEAR_OF_PUBLICATION));
    }
}
