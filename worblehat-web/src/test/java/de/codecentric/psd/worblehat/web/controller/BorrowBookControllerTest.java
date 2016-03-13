package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookAlreadyBorrowedException;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class BorrowBookControllerTest {

    private BookService bookService;

    private  BorrowBookController borrowBookController;

    private BindingResult bindingResult;

    private BookBorrowFormData bookBorrowFormData;

    private static final Book TEST_BOOK = new Book("title", "author", "edition", "isbn", 2016);
    public static final String BORROWER_EMAIL = "someone@codecentric.de";

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        bindingResult = mock(BindingResult.class);
        bookBorrowFormData = mock(BookBorrowFormData.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        borrowBookController = new BorrowBookController(bookService);
    }

    @Test
    public void shouldNavigateToBorrowWhenResultHasErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldRejectBorrowingIfBookDoesNotExist() throws Exception {
        when(bookService.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(null);
        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
        verify(bindingResult).rejectValue("isbn", "notBorrowable");
        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldRejectAlreadyBorrowedBooks() throws Exception {
        when(bookBorrowFormData.getEmail()).thenReturn(BORROWER_EMAIL);
        when(bookBorrowFormData.getIsbn()).thenReturn(TEST_BOOK.getIsbn());
        when(bookService.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(TEST_BOOK);
        doThrow(BookAlreadyBorrowedException.class).when(bookService).borrowBook(TEST_BOOK, BORROWER_EMAIL);
        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
        verify(bindingResult).rejectValue("isbn", "internalError");
        assertThat(navigateTo, is("borrow"));
    }

    @Test
    public void shouldNavigateHomeOnSuccess() throws Exception {
        when(bookBorrowFormData.getEmail()).thenReturn(BORROWER_EMAIL);
        when(bookBorrowFormData.getIsbn()).thenReturn(TEST_BOOK.getIsbn());
        when(bookService.findBookByIsbn(TEST_BOOK.getIsbn())).thenReturn(TEST_BOOK);
        String navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult);
        verify(bookService).borrowBook(TEST_BOOK, BORROWER_EMAIL);
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldNavigateToHomeOnErrors() throws Exception {
        String navigateTo = borrowBookController.handleErrors(mock(Exception.class), mock(HttpServletRequest.class));
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = mock(ModelMap.class);
        borrowBookController.setupForm(modelMap);
        verify(modelMap).put("borrowFormData", BookBorrowFormData.class);
    }
}
