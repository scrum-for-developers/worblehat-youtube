package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InsertBookControllerTest {

    private InsertBookController insertBookController;

    private BookService bookService;

    private BookDataFormData bookDataFormData;

    private BindingResult bindingResult;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        insertBookController = new InsertBookController(bookService);
        bookDataFormData = mock(BookDataFormData.class);
        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = mock(ModelMap.class);
        insertBookController.setupForm(modelMap);
        verify(modelMap).put(eq("bookDataFormData"), any(BookDataFormData.class));
    }

    @Test
    public void shouldRejectErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);
        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    public void shouldRejectExistingBooks() throws Exception {
        when(bookDataFormData.getIsbn()).thenReturn(TEST_BOOK.getIsbn());
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(true);
        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);
        verify(bindingResult).rejectValue("isbn", "duplicateIsbn");
        assertThat(navigateTo, is("insertBooks"));
    }

    @Test
    public void shouldCreateBookAndNavigateToBookList() throws Exception {
        when(bookDataFormData.getTitle()).thenReturn(TEST_BOOK.getTitle());
        when(bookDataFormData.getAuthor()).thenReturn(TEST_BOOK.getAuthor());
        when(bookDataFormData.getEdition()).thenReturn(TEST_BOOK.getEdition());
        when(bookDataFormData.getIsbn()).thenReturn(TEST_BOOK.getIsbn());
        when(bookDataFormData.getYearOfPublication()).thenReturn(String.valueOf(TEST_BOOK.getYearOfPublication()));
        when(bookService.bookExists(TEST_BOOK.getIsbn())).thenReturn(false);
        String navigateTo = insertBookController.processSubmit(bookDataFormData, bindingResult);
        verify(bookService).createBook(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(),
                TEST_BOOK.getEdition(), TEST_BOOK.getIsbn(), TEST_BOOK.getYearOfPublication());
        assertThat(navigateTo, is("redirect:bookList"));
    }
}
