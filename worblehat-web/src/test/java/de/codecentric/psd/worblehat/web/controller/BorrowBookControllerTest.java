package de.codecentric.psd.worblehat.web.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookAlreadyBorrowedException;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BorrowBookControllerTest {

    private BookService bookService;

    private  BorrowBookController borrowBookController;

    private BindingResult bindingResult;

    private BookBorrowFormData bookBorrowFormData;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);

    public static final String BORROWER_EMAIL = "someone@codecentric.de";

    @Before
    public void setUp() {
        bookService = mock(BookService.class);
        bindingResult = new MapBindingResult(new HashMap<>(), "");
        bookBorrowFormData = new BookBorrowFormData();
        borrowBookController = new BorrowBookController(bookService);
    }

    @Test
    public void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        borrowBookController.setupForm(modelMap);

        assertThat(modelMap.get("borrowFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldNavigateToBorrowWhenResultHasErrors() {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldRejectBorrowingIfBookDoesNotExist() {
        when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(null);

        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

        assertThat(bindingResult.hasFieldErrors("isbn"), is(true));
        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldRejectAlreadyBorrowedBooks() throws Exception {
        bookBorrowFormData.setEmail(BORROWER_EMAIL);
        bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
        when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(Collections.singleton(TEST_BOOK));
        doThrow(BookAlreadyBorrowedException.class).when(bookService).borrowOneBook(anySetOf(Book.class), eq(BORROWER_EMAIL));

        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);

        assertThat(bindingResult.hasFieldErrors("isbn"), is(true));
        assertThat(bindingResult.getFieldError("isbn").getCode(), is("noBorrowableBooks"));
        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldNavigateHomeOnSuccess() throws Exception {
        bookBorrowFormData.setEmail(BORROWER_EMAIL);
        bookBorrowFormData.setIsbn(TEST_BOOK.getIsbn());
        when(bookService.findBooksByIsbn(TEST_BOOK.getIsbn())).thenReturn(Collections.singleton(TEST_BOOK));

        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
        verify(bookService).borrowOneBook(anySetOf(Book.class), eq(BORROWER_EMAIL));
        verify(bookService).borrowOneBook((Set<Book>) Matchers.argThat(org.hamcrest.Matchers.contains(TEST_BOOK)), eq(BORROWER_EMAIL));
//        verify(bookService).borrowOneBook(TEST_BOOK, BORROWER_EMAIL);
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldNavigateToHomeOnErrors() {
        String navigateTo = borrowBookController.handleErrors(new Exception(), new MockHttpServletRequest());

        assertThat(navigateTo, is("home"));
    }
}
