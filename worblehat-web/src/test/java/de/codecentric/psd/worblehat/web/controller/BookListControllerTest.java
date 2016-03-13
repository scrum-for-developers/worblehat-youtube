package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.StandardBookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookListControllerTest {

    private BookService bookService;

    private BookListController bookListController;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    private ModelMap modelMap;

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        bookListController = new BookListController(bookService);
        modelMap = new ModelMap();
    }

    @Test
    public void shouldNavigateToBookList() throws Exception {
        String url = bookListController.setupForm(modelMap);
        assertThat(url, is("bookList"));
    }

    @Test
    public void shouldContainBooks() throws Exception {
        List<Book> bookList = new ArrayList();
        bookList.add(TEST_BOOK);
        when(bookService.findAllBooks()).thenReturn(bookList);
        bookListController.setupForm(modelMap);
        List<Book> actualBooks = (List<Book>)modelMap.get("books");
        assertThat(actualBooks, is(bookList));
    }

}
