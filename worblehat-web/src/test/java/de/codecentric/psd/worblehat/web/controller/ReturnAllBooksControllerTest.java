package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
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

public class ReturnAllBooksControllerTest {

    private ReturnAllBooksController returnAllBooksController;

    private BookService bookService;

    private ReturnAllBooksFormData returnAllBooksFormData;

    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        returnAllBooksController = new ReturnAllBooksController(bookService);
        returnAllBooksFormData = mock(ReturnAllBooksFormData.class);
        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = mock(ModelMap.class);
        returnAllBooksController.prepareView(modelMap);
        verify(modelMap).put(eq("returnAllBookFormData"), any(ReturnAllBooksFormData.class));
    }

    @Test
    public void shouldRejectErrors() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(true);
        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);
        assertThat(navigateTo, is("returnAllBooks"));
    }

    @Test
    public void shouldReturnAllBooksAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        when(returnAllBooksFormData.getEmailAddress()).thenReturn(borrower);
        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);
        verify(bookService).returnAllBooksByBorrower(borrower);
        assertThat(navigateTo, is("home"));
    }
}
